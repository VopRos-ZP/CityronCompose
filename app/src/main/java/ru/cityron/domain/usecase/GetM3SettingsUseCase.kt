package ru.cityron.domain.usecase

import kotlinx.serialization.json.Json
import ru.cityron.domain.model.JsonSettings
import ru.cityron.domain.model.m3.M3Settings
import ru.cityron.domain.repository.NetworkRepository
import ru.cityron.domain.utils.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetM3SettingsUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {

    suspend operator fun invoke(): M3Settings {
        return fromJson<JsonSettings<M3Settings>>(networkRepository.get(Path.JSON_SETTINGS)).settings
    }

    private inline fun <reified T> fromJson(string: String): T = Json.decodeFromString(string)

}