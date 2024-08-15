package ru.cityron.domain.usecase.auth

import ru.cityron.data.model.ServerAuthResponse
import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource
import ru.cityron.domain.model.m3.M3All
import ru.cityron.domain.repository.HttpRepository
import ru.cityron.domain.usecase.device.GetDeviceUseCase
import ru.cityron.domain.utils.Path.JSON_STATE
import ru.cityron.domain.utils.fromJson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerAuthUseCase @Inject constructor(
    private val httpRepository: HttpRepository,
    private val getDeviceUseCase: GetDeviceUseCase,
) {

    suspend operator fun invoke(controller: Controller): Triple<M3All, DataSource, String> {
        val device = getDeviceUseCase()
        val idCpu = controller.idCpu.lowercase()
        val token = fromJson<ServerAuthResponse>(
            httpRepository.post(
                url = "https://api.rcserver.ru/auth",
                body = "$idCpu\n${device.deviceId}"
            )
        ).token
        // save token

        // get M3All request
        return Triple(
            first = fromJson<M3All>(
                httpRepository.get(
                    url = "https://rcserver.ru/rc/$idCpu/$JSON_STATE",
                    token = token
                )
            ),
            second = DataSource.REMOTE,
            third = token
        )
    }

}