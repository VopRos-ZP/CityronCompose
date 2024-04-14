package ru.cityron.core.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json
import ru.cityron.core.domain.model.KeyError
import ru.cityron.core.domain.repository.CControllerRepository
import ru.cityron.core.domain.repository.NetworkRepository
import ru.cityron.core.domain.utils.Path
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val cControllerRepository: CControllerRepository,
) : NetworkRepository {

    override suspend fun get(path: String): String = request(path, HttpMethod.Get)

    override suspend fun post(path: String, body: String?): String = request(path, HttpMethod.Post) {
        body?.let(this::setBody)
    }

    private suspend fun request(
        url: String, m: HttpMethod,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): String {
        return cControllerRepository.controller?.let {
            return try {
                httpClient.request {
                    url("http://${it.ipAddress}/$url")
                    method = m
                    builder()
                }.body()
            } catch (_: Exception) {
                try {
                    httpClient.request {
                        url("https://rcserver.ru/rc/${it.idCpu}/$url")
                        method = m
                        builder()
                    }.body()
                } catch (_: Exception) {
                    ""
                }
            }
        } ?: ""
    }

    override suspend fun conf(key: String, value: Any): Result<String> {
        val body = post(Path.CONF, "$key=$value")
        return when (body.contains("ok")) {
            true -> Result.success("ok")
            else -> Result.failure(
                Json.decodeFromString<KeyError>(body).let {
                    Throwable(message = "${it.error} ${it.key}")
                }
            )
        }
    }

}