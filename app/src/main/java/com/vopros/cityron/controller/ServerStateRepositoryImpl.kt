package com.vopros.cityron.controller

import com.vopros.cityron.utils.Network.BASE_URL
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
import javax.inject.Inject

class ServerStateRepositoryImpl @Inject constructor(
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

    override suspend fun fetchState(ipOrControllerId: String): String {
        val cookie = login()
        return httpClient.get("$BASE_URL/$ipOrControllerId/json?state") {
            cookie(cookie.name, cookie.value)
        }.body()
    }

}