package ru.cityron.data.repository

import ru.cityron.data.model.BindsAddResponse
import ru.cityron.domain.repository.BindCurrentRepository
import ru.cityron.domain.repository.BindRepository
import ru.cityron.domain.repository.HttpRepository
import ru.cityron.domain.utils.Path.toQueryParams
import ru.cityron.domain.utils.fromJson
import javax.inject.Inject

class BindRepositoryImpl @Inject constructor(
    private val httpRepository: HttpRepository,
    private val bindCurrentRepository: BindCurrentRepository,
) : BindRepository {

    override suspend fun auth(num: String, appUuid: String): Boolean {
        val body = listOf(num, appUuid).toQueryParams()
        return request("auth", body).contains("ok")
    }

    override suspend fun auth(pass: String, level: Char): Boolean {
        return request("auth", "$pass\n$level").contains("ok")
    }

    override suspend fun add(accessLevel: String, appUuid: String, phoneInfo: String): String {
        val body = listOf(accessLevel, appUuid, phoneInfo).toQueryParams()
        return fromJson<BindsAddResponse>(request("binds?add", body)).num.toString()
    }

    override suspend fun cancel() {
        request("binds?cancel", "")
    }

    override suspend fun confirm(code: String): BindsAddResponse {
        return fromJson(request("binds?confirm", code))
    }

    override suspend fun delete(num: String, appUuid: String): Boolean {
        val body = listOf(num, appUuid).toQueryParams()
        return request("binds?del", body).contains("ok")
    }

    private suspend fun request(path: String, body: String): String {
        val ip = bindCurrentRepository.controller.ipAddress
        return httpRepository.post("http://$ip/$path", body = body)
    }

}