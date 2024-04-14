package ru.cityron.core.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ru.cityron.core.domain.model.Info
import ru.cityron.core.domain.repository.UdpRepository
import ru.cityron.core.domain.utils.Udp.HOST
import ru.cityron.core.domain.utils.Udp.MESSAGE
import ru.cityron.core.domain.utils.Udp.PORT_RECEIVE
import ru.cityron.core.domain.utils.Udp.PORT_SEND
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import javax.inject.Inject

class UdpRepositoryImpl @Inject constructor() : UdpRepository {

    private val broadcastAddress: InetAddress = InetAddress.getByName(HOST)
    private lateinit var socket: DatagramSocket

    private suspend fun send(msg: String) = coroutineScope {
        return@coroutineScope async {
            val clientSocket = DatagramSocket()
            clientSocket.broadcast = true
            val sendData = msg.toByteArray()
            val sendPacket = DatagramPacket(
                sendData, sendData.size, broadcastAddress, PORT_SEND
            )
            clientSocket.send(sendPacket)
        }
    }

    override suspend fun receive(size: Int) = coroutineScope {
        return@coroutineScope async(Dispatchers.IO) {
            socket = DatagramSocket(PORT_RECEIVE)
            send(MESSAGE).await()
            val buf = ByteArray(size)
            val packet = DatagramPacket(buf, buf.size)
            socket.receive(packet)
            return@async transform(packet.data.decodeToString())
        }
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
    }

    override fun close() {
        socket.close()
    }

}