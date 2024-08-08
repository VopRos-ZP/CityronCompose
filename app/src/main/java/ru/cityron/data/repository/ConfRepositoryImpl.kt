package ru.cityron.data.repository

import android.util.Log
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.NetworkRepository
import ru.cityron.domain.utils.Path.CONF
import javax.inject.Inject

class ConfRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository
) : ConfRepository {

    override suspend fun conf(key: String, value: Any) {
        val body = networkRepository.post(CONF, body = "$key=$value\n")
        if (!body.contains("\"result\":\"ok\"")) {
            Log.e("ConfRepository", body)
            throw RuntimeException() // Error
        }
    }

}