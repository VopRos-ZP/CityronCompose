package ru.cityron.network.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.cookie
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Cookie
import io.ktor.http.setCookie
import ru.cityron.network.ControllerStateRepository
import ru.cityron.network.LogResult
import ru.cityron.network.Network.BASE_URL
import javax.inject.Inject

class ServerControllerStateRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : ControllerStateRepository {

    private val multiPartContent = MultiPartFormDataContent(formData {
        append("login", "zolpavel366@gmail.com")
        append("pass", "VopRos366")
    })

    private suspend fun login(): Cookie {
        return httpClient.post("$BASE_URL/auth") {
            setBody(multiPartContent)
        }.setCookie().first()
    }

    override suspend fun getState(ipOrControllerId: String): String {
        val cookie = login()
        return httpClient.get("$BASE_URL/$ipOrControllerId/json?state") {
            cookie(cookie.name, cookie.value)
        }.body()
    }

    override suspend fun updateState(ipOrControllerId: String, key: String, value: Any) {
        val cookie = login()
        httpClient.post("$BASE_URL/$ipOrControllerId/conf") {
            cookie(cookie.name, cookie.value)
            setBody("$key=$value")
        }
    }

    override suspend fun fetchLog(ipOrControllerId: String, count: Int, types: Int, sources: Int, reasons: Int): LogResult {
        return httpClient
            .get("$BASE_URL/$ipOrControllerId/json?events&count=$count&types=$types&sources=$sources&reasons=$reasons")
            .body()
    }

}