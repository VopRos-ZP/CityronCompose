package com.vopros.cityron.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vopros.cityron.controller.ControllerItem
import com.vopros.cityron.local.LocalStore
import com.vopros.cityron.m3.domain.M3State
import com.vopros.cityron.repository.controllerState.ControllerStateRepository
import com.vopros.cityron.repository.controllerState.LocalStateRepo
import com.vopros.cityron.repository.controllerState.ServerStateRepo
import com.vopros.cityron.repository.wifi.WifiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localStore: LocalStore,
    private val wifiRepository: WifiRepository,
    @LocalStateRepo
    private val localStateRepository: ControllerStateRepository,
    @ServerStateRepo
    private val serverStateRepository: ControllerStateRepository
) : ViewModel() {

    private val _controllers = MutableStateFlow<List<Pair<ControllerItem, Int>>>(emptyList())
    val controllers = _controllers.asStateFlow()

    fun fetchControllers() {
        viewModelScope.launch {
            localStore.data.collect { s ->
                wifiRepository.isInLocalNetwork().collect { i ->
                    val fetchState: suspend (ControllerStateRepository, String) -> Int = { repo, id ->
                        try {
                            Json.decodeFromString<M3State>(repo.getState(id))
                            0
                        } catch (_: Exception) {
                            1
                        }
                    }
                    val controllers = s.tokens.map { t -> t.controller }
                    val res = controllers.map { controller ->
                        when (i) {
                            true -> when (val local = fetchState(localStateRepository, controller.ipAddress)) {
                                0 -> Pair(controller, 0)
                                else -> Pair(controller, local)
                            }
                            else -> Pair(controller, fetchState(serverStateRepository, controller.idCpu))
                        }
                    }
                    _controllers.emit(res)
                }
            }
        }
    }

}