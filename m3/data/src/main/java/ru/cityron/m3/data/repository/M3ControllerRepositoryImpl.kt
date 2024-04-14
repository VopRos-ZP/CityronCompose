package ru.cityron.m3.data.repository

import kotlinx.serialization.json.Json
import ru.cityron.core.domain.repository.NetworkRepository
import ru.cityron.core.domain.utils.Path
import ru.cityron.m3.domain.model.M3All
import ru.cityron.m3.domain.repository.M3ControllerRepository
import javax.inject.Inject

class M3ControllerRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository
) : M3ControllerRepository {

    override suspend fun conf(key: String, value: Any): Result<String> {
        return networkRepository.conf(key, value)
    }

    override suspend fun fetchAll(): Result<M3All> {
        return try {
            val body = networkRepository.get(Path.JSON_ALL)
            Result.success(Json.decodeFromString(body))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}