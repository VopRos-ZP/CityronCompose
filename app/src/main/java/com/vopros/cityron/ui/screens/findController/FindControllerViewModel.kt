package com.vopros.cityron.ui.screens.findController

import com.vopros.cityron.utils.UDP.HOST
import com.vopros.cityron.utils.UDP.PORT_SEND
import com.vopros.cityron.utils.UDP.PORT_RECEIVE
import com.vopros.cityron.utils.UDP.MESSAGE
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vopros.cityron.controller.ControllerItem
import com.vopros.cityron.local.Ids
import com.vopros.cityron.local.Info
import com.vopros.cityron.local.LocalStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import javax.inject.Inject

@HiltViewModel
class FindControllerViewModel @Inject constructor(
    private val localStore: LocalStore
) : ViewModel() {

    var isServerRunning by mutableStateOf(false)

    private val address = InetAddress.getByName(HOST)
    private var socket: DatagramSocket? = null

    private val _info = MutableStateFlow<List<Pair<Info, Boolean>>?>(null)
    val info = _info.asStateFlow()

    fun toggleServer() {
        if (isServerRunning) {
            stopServer()
        } else {
            startServer()
        }
        isServerRunning = !isServerRunning
    }

    private fun sendUdp() {
        val socket = DatagramSocket()
        socket.broadcast = true
        socket.reuseAddress = true
        val byteArray = MESSAGE.toByteArray()
        socket.send(DatagramPacket(byteArray, 0, byteArray.size, address, PORT_SEND))
        socket.close()
    }

    private fun startServer() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                socket = DatagramSocket(PORT_RECEIVE)
                val list = mutableListOf<Info>()
                while (isServerRunning) {
                    sendUdp()
                    val buffer = ByteArray(1024)
                    val packet = DatagramPacket(buffer, buffer.size)
                    socket?.receive(packet)
                    val message = String(packet.data, 0, packet.length)
                    val map = message
                        .lines()
                        .dropLast(1)
                        .associate { s ->
                            val (f, e) = s.split("=")
                            f to e
                        }

                    val info = Info(
                        cmd = map["cmd"]!!,
                        type = map["type"]!!.toInt(),
                        idUsr = map["idUsr"]!!,
                        idCpu = map["idCpu"]!!,
                        devName = map["devName"]!!,
                        name = map["name"]!!,
                        usePass = map["usePass"]!!.toInt(),
                        dhcp = map["dhcp"]!!.toInt(),
                        ip = map["ip"]!!,
                        mask = map["mask"]!!,
                        setIp = map["setIp"]!!,
                    )
                    if (!list.map { l -> l.idCpu }.contains(info.idCpu)) {
                        list.add(info)
                    }
                    //
                    localStore.data.collect { s ->
                        val cpus = s.tokens.map { t -> t.controller.idCpu }
                        val res = list.map { c -> c to cpus.contains(c.idCpu) }
//                        for (ip in list.map { i -> i.ip }) {
//                            Log.d("TAG", "$ip -> ${controllerRepository.bindApps(ip)}")
//                        }
                        _info.emit(res)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun stopServer() {
        socket?.close()
    }

    fun addController(info: Info) {
        viewModelScope.launch {
            localStore.update {
                val newTokens = tokens.toMutableList().apply {
                    add(
                        Ids(
                            localToken = "",
                            serverToken = "",
                            controller = ControllerItem(
                                name = "${info.devName}#${info.idUsr}",
                                ipAddress = info.ip,
                                idCpu = info.idCpu
                            )
                        )
                    )
                }
                //val res = controllerRepository.bindController(info.ip, uuid, DeviceInfo.getDeviceName())
                //Log.d("TAG", res)
                copy(tokens = newTokens)
            }
        }
    }

}