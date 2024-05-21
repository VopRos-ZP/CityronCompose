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
        return sendControllerRequest(path, httpRepository::get)
    }

    override suspend fun post(path: String, body: String): String {
        return sendControllerRequest(path) { httpRepository.post(it, body) }
    }

    private suspend fun sendControllerRequest(
        suffix: String,
        httpSend: suspend (String) -> String
    ): String {
        val (controller, source) = when (val cur = currentRepository.current) {
            null -> throw RuntimeException("Current controller can't be null")
            else -> cur
        }
        return when (source) {
            is DataSource.Local -> httpSend("http://${controller.ipAddress}/$suffix")
            is DataSource.Remote -> httpSend("https://rcserver.ru/rc/${controller.idCpu}/$suffix")
        }
    }

}