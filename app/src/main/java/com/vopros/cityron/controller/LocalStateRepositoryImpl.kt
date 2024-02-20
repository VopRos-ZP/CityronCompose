package com.vopros.cityron.controller

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class LocalStateRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : ControllerStateRepository {

    override suspend fun fetchState(ipOrControllerId: String): String {
        return httpClient.get("http://$ipOrControllerId/json?state").body()
    }

}