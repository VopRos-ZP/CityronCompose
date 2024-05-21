package ru.cityron.data.repository

import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.NetworkRepository
import ru.cityron.domain.utils.Path.CONF
import javax.inject.Inject

class ConfRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository
) : ConfRepository {

    override suspend fun conf(key: String, value: Any): Boolean {
        val body = networkRepository.post(CONF, body = "$key=$value")
        return !body.contains("error")
    }

}