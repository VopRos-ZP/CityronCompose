package com.vopros.cityron.controller

interface ControllerStateRepository {
    suspend fun fetchState(ipOrControllerId: String) : String
}