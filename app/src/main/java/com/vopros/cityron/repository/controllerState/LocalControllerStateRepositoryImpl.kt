package com.vopros.cityron.repository.controllerState

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class LocalControllerStateRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : ControllerStateRepository {

    override suspend fun getState(ipOrControllerId: String): String {
        return httpClient.get("http://$ipOrControllerId/json?state").body()
    }

    override suspend fun updateState(ipOrControllerId: String, key: String, value: Any) {
        httpClient.post("http://$ipOrControllerId/conf") {
            setBody("$key=$value")
        }
    }

}