package com.vopros.cityron.m3.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vopros.cityron.controller.ControllerItem
import com.vopros.cityron.controller.LocalStateRepositoryImpl
import com.vopros.cityron.controller.ServerStateRepositoryImpl
import com.vopros.cityron.m3.domain.M3State
import com.vopros.cityron.wifi.WifiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class M3ScreenViewModel @Inject constructor(
    private val wifiRepository: WifiRepository,
    private val localStateRepository: LocalStateRepositoryImpl,
    private val serverStateRepository: ServerStateRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow<M3State?>(null)
    val state = _state.asStateFlow()

    private val isListening = MutableStateFlow(false)

    fun listenState(controllerItem: ControllerItem) {
        viewModelScope.launch {
            isListening.emit(true)
            isListening.combine(wifiRepository.isInLocalNetwork()) { a, b -> Pair(a, b) }.collect { (it, isInLocalNetwork) ->
                while (it) {
                    val (stateRepository, id) = when (isInLocalNetwork) {
                        true -> Pair(localStateRepository, controllerItem.ipAddress)
                        else -> Pair(serverStateRepository, controllerItem.idCpu)
                    }
                    _state.emit(Json.decodeFromString(stateRepository.fetchState(id)))
                }
            }
        }
    }

    fun disposeState() {
        viewModelScope.launch {
            isListening.emit(false)
        }
    }

}