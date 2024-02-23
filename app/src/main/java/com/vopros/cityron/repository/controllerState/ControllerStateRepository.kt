package com.vopros.cityron.repository.controllerState

interface ControllerStateRepository {
    suspend fun getState(ipOrControllerId: String): String
    suspend fun updateState(ipOrControllerId: String, key: String, value: Any)
}