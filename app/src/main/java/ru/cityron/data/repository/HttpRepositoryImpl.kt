package ru.cityron.data.repository

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import ru.cityron.domain.repository.HttpRepository
import javax.inject.Inject

class HttpRepositoryImpl @Inject constructor(
    private val httpClient: OkHttpClient
) : HttpRepository {

    override suspend fun get(url: String): String {
        val request = Request.Builder()
            .get()
            .url(url)
            .build()
        return try {
            httpClient.newCall(request).execute().body?.string() ?: ""
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

    override suspend fun post(url: String, body: String): String {
        val request = Request.Builder()
            .post(body.toRequestBody("text/plain".toMediaType()))
            .url(url)
            .build()
        return try {
            httpClient.newCall(request).execute().body?.string() ?: ""
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

}