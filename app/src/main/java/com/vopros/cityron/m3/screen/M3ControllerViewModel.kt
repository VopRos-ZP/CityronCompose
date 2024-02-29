package com.vopros.cityron.m3.screen

import com.vopros.cityron.m3.domain.M3State
import com.vopros.cityron.repository.controllerState.ControllerStateRepository
import com.vopros.cityron.repository.controllerState.LocalStateRepo
import com.vopros.cityron.repository.controllerState.ServerStateRepo
import com.vopros.cityron.repository.wifi.WifiRepository
import com.vopros.cityron.ui.screens.controller.ControllerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class M3ControllerViewModel @Inject constructor(
    wifiRepository: WifiRepository,
    @LocalStateRepo localStateRepository: ControllerStateRepository,
    @ServerStateRepo serverStateRepository: ControllerStateRepository
) : ControllerViewModel<M3State>(
    wifiRepository = wifiRepository,
    localStateRepository = localStateRepository,
    serverStateRepository = serverStateRepository,
    convert = { Json.decodeFromString(it) },
    default = M3State()
)