package ru.cityron.domain.usecase.access

import ru.cityron.domain.model.JsonSettings
import ru.cityron.domain.model.m3.M3Settings
import ru.cityron.domain.repository.BindCurrentRepository
import ru.cityron.domain.repository.HttpRepository
import ru.cityron.domain.utils.Path.JSON_SETTINGS
import ru.cityron.domain.utils.fromJson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAccessUseCase @Inject constructor(
    private val bindCurrentRepository: BindCurrentRepository,
    private val httpRepository: HttpRepository,
) {

    suspend operator fun invoke(accessLevel: String): Pair<Boolean, Int> {
        val controller = bindCurrentRepository.controller!!
        val http = fromJson<JsonSettings<M3Settings>>(
            httpRepository.get("http://${controller.ipAddress}/$JSON_SETTINGS")
        ).settings.http
        val (p, pass) = when (accessLevel) {
            "r" -> http.fPr to http.pr
            "u" -> http.fPu to http.pu
            "w" -> http.fPw to http.pw
            else ->  throw RuntimeException("Unknown AccessLevel: `$accessLevel`")
        }
        return (p == 1) to pass.length
    }

}