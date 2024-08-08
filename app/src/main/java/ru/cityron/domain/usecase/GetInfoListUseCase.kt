package ru.cityron.domain.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.Info
import ru.cityron.domain.model.Ip
import ru.cityron.domain.model.m3.M3All
import ru.cityron.domain.repository.ControllerRepository
import ru.cityron.domain.repository.HttpRepository
import ru.cityron.domain.repository.IpRepository
import ru.cityron.domain.repository.UdpRepository
import ru.cityron.domain.utils.Path.JSON_ALL
import ru.cityron.domain.utils.fromJson
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetInfoListUseCase @Inject constructor(
    private val udpRepository: UdpRepository,
    private val httpRepository: HttpRepository,
    private val ipRepository: IpRepository,
    private val controllerRepository: ControllerRepository
) {

    private val atlasTestInfo = Info(
        cmd = "info",
        type = 4,
        idUsr = "C4A6DA8D",
        idCpu = "335541503437330734545957", // 4A0F41503437330634545957
        devName = "atlas",
        name = "",
        usePass = 0,
        dhcp = 1,
        ip = "192.168.1.163",
        mask = "255.255.255.0",
        setIp = "192.168.1.163"
    )

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val infoList: Flow<Map<Info, Boolean>> = flow {
        val list = mutableListOf<Info>()
        try {
            while (true) {
                list.clear()

                list.add(atlasTestInfo)
                val infoList = getInfoListByIps(ipRepository.fetchAll())
                try {
                    infoList.add(udpRepository.receive())
                } catch (_: SocketTimeoutException) {}

                for (info in infoList.distinctBy { i -> i.idCpu }) {
                    if (!list.contains(info)) {
                        list.add(info)
                    }
                }
                emit(list.associateWith { i -> controllerRepository.fetchAll().map(Controller::idCpu).contains(i.idCpu) })
                udpRepository.close()
                delay(1000)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = emptyMap()
    )

    private suspend fun getInfoListByIps(ipList: List<Ip>): MutableList<Info> {
        val list = mutableListOf<Info>()
        for (ip in ipList) {
            val all = fromJson<M3All>(httpRepository.get("http://${ip.address}/$JSON_ALL"))
            list.add(
                Info(
                    cmd = "info",
                    type = 4,
                    idCpu = all.static.idCpu,
                    idUsr = all.static.idUsr,
                    devName = all.static.devName,
                    name = all.settings.others.loc,
                    usePass = 0,
                    dhcp = all.settings.eth.fDhcp,
                    ip = ip.address,
                    mask = all.settings.eth.mask,
                    setIp = all.settings.eth.ip
                )
            )
        }
        return list
    }

}