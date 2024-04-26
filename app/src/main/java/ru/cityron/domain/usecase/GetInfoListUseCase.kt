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
import ru.cityron.domain.repository.UdpRepository
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetInfoListUseCase @Inject constructor(
    private val udpRepository: UdpRepository,
    private val controllersUseCase: GetControllersUseCase
) {

    private val atlasTestInfo = Info(
        cmd = "info",
        type = 0,
        idUsr = "C4A6DA8D",
        idCpu = "335541503437330734545957", // 4a0f41503437330634545957
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
        controllersUseCase.controllers.collect {
            val list = mutableListOf<Info>()
            val added = it.map(Controller::idCpu)
            try {
                while (true) {
                    list.clear()

                    list.add(atlasTestInfo)

                    val info = udpRepository.receive()
                    if (!list.contains(info)) {
                        list.add(info)
                    }
                    emit(list.associateWith { i -> added.contains(i.idCpu) })
                    udpRepository.close()
                    delay(1000)
                }
            } catch (_: SocketTimeoutException) {
                emit(list.associateWith { i -> added.contains(i.idCpu) })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = emptyMap()
    )

}