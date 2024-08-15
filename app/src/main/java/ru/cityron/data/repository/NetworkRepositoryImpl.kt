package ru.cityron.data.repository

import ru.cityron.domain.model.DataSource
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.repository.HttpRepository
import ru.cityron.domain.repository.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val httpRepository: HttpRepository,
    private val currentRepository: CurrentRepository,
): NetworkRepository {

    override suspend fun get(path: String): String {
        return sendControllerRequest(path) { url, token ->
            httpRepository.get(url = url, token = token)
        }
    }

    override suspend fun post(path: String, body: String): String {
        return sendControllerRequest(path) { url, token ->
            httpRepository.post(url = url, body = body, token = token)
        }
    }

    private suspend fun sendControllerRequest(
        suffix: String,
        httpSend: suspend (String, String?) -> String
    ): String {
        val controller = when (val cur = currentRepository.current.value) {
            null -> throw RuntimeException("Current controller can't be null")
            else -> cur
        }
        return when (controller.status.source) {
            DataSource.LOCAL -> httpSend(
                "http://${controller.ipAddress}/$suffix",
                controller.token
            )
            DataSource.REMOTE -> httpSend(
                "https://rcserver.ru/rc/${controller.idCpu}/$suffix",
                controller.token
            )
        }
    }

}