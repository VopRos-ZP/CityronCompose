package ru.cityron.domain.usecase

import kotlinx.serialization.json.Json
import ru.cityron.domain.model.JsonSched
import ru.cityron.domain.model.m3.M3Sched
import ru.cityron.domain.repository.NetworkRepository
import ru.cityron.domain.utils.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetM3SchedUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
) {

    suspend operator fun invoke(): M3Sched {
        val result = fromJson<JsonSched<M3Sched>>(networkRepository.get(Path.JSON_SCHED)).sched
        return result
    }

    private inline fun <reified T> fromJson(string: String): T = Json.decodeFromString(string)

}