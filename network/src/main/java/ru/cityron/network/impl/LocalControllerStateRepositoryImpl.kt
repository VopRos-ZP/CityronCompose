package ru.cityron.network.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ru.cityron.network.ControllerStateRepository
import ru.cityron.network.LogResult
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

    override suspend fun fetchLog(ipOrControllerId: String, count: Int, types: Int, sources: Int, reasons: Int): LogResult {
        return httpClient
            .get("http://$ipOrControllerId/json?events&count=$count&types=$types&sources=$sources&reasons=$reasons")
            .body()
    }

}