package ru.cityron.network

interface ControllerStateRepository {
    suspend fun getState(ipOrControllerId: String): String
    suspend fun updateState(ipOrControllerId: String, key: String, value: Any)
    suspend fun fetchLog(
        ipOrControllerId: String,
        count: Int,
        types: Int,
        sources: Int,
        reasons: Int
    ) : LogResult
}