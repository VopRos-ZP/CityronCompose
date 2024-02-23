package com.vopros.cityron.ui.screens.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vopros.cityron.controller.ControllerItem
import com.vopros.cityron.repository.controllerState.ControllerStateRepository
import com.vopros.cityron.repository.wifi.WifiRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class ControllerViewModel<T> (
    private val wifiRepository: WifiRepository,
    private val localStateRepository: ControllerStateRepository,
    private val serverStateRepository: ControllerStateRepository,
    private val convert: (String) -> T
) : ViewModel() {

    private val _state = MutableStateFlow<T?>(null)
    val state = _state.asStateFlow()

    fun fetchState(controller: ControllerItem) {
        viewModelScope.launch {
            wifiRepository.isInLocalNetwork().collect {
                while (true) {
                    try {
                        val (repo, id) = when (it) {
                            true -> Pair(localStateRepository, controller.ipAddress)
                            else -> Pair(serverStateRepository, controller.idCpu)
                        }
                        _state.emit(convert(repo.getState(id)))
                    } catch (e: Exception) {
                        _state.emit(null)
                    }
                    delay(1000)
                }
            }
        }
    }

    fun updateState(controller: ControllerItem, key: String, value: Any) {
        viewModelScope.launch {
            wifiRepository.isInLocalNetwork().collect {
                val (repo, id) = when (it) {
                    true -> Pair(localStateRepository, controller.ipAddress)
                    else -> Pair(serverStateRepository, controller.idCpu)
                }
                repo.updateState(id, key, value)
            }
        }
    }

}