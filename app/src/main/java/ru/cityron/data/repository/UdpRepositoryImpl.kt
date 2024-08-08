package ru.cityron.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.cityron.domain.model.Info
import ru.cityron.domain.repository.UdpRepository
import ru.cityron.domain.utils.Udp.HOST
import ru.cityron.domain.utils.Udp.MESSAGE
import ru.cityron.domain.utils.Udp.PORT_RECEIVE
import ru.cityron.domain.utils.Udp.PORT_SEND
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import javax.inject.Inject

class UdpRepositoryImpl @Inject constructor() : UdpRepository {

    private val broadcastAddress: InetAddress = InetAddress.getByName(HOST)
    private var socket: DatagramSocket? = null

    private suspend fun send(msg: String) {
        val clientSocket = withContext(Dispatchers.IO) {
            DatagramSocket()
        }
        clientSocket.broadcast = true
        val sendData = msg.toByteArray()
        val sendPacket = DatagramPacket(
            sendData, sendData.size, broadcastAddress, PORT_SEND
        )
        withContext(Dispatchers.IO) {
            clientSocket.send(sendPacket)
        }
    }

    override suspend fun receive(size: Int): Info {
        if (socket == null) {
            socket = withContext(Dispatchers.IO) {
                DatagramSocket(PORT_RECEIVE).apply { soTimeout = 1000 }
            }
        }
        send(MESSAGE)
        val buf = ByteArray(size)
        val packet = DatagramPacket(buf, buf.size)
        withContext(Dispatchers.IO) {
            socket?.receive(packet)
        }
        return transform(packet.data.decodeToString())
    }

    private fun transform(it: String): Info {
        val map = it
            .lines()
            .dropLast(1)
            .associate { s ->
                val (f, e) = s.split("=")
                f to e
            }

        return Info(
            cmd = map["cmd"]!!,
            type = map["type"]!!.toInt(),
            idUsr = map["idUsr"]!!.uppercase(),
            idCpu = map["idCpu"]!!.uppercase(),
            devName = map["devName"]!!,
            name = map["name"]!!,
            usePass = map["usePass"]!!.toInt(),
            dhcp = map["dhcp"]!!.toInt(),
            ip = map["ip"]!!,
            mask = map["mask"]!!,
            setIp = map["setIp"]!!,
        )
    }

    override fun close() {
        socket?.close()
        socket = null
    }

}