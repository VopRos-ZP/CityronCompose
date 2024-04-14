package com.vopros.cityron.ui.screens.findController

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onErrorReturn
import ru.cityron.core.data.usecase.ControllerUseCase
import ru.cityron.core.data.usecase.UdpUseCase
import ru.cityron.core.domain.model.Info
import ru.cityron.core.domain.utils.Resource
import javax.inject.Inject

@HiltViewModel
class FindControllerViewModel @Inject constructor(
    private val udpUseCase: UdpUseCase,
    private val controllerUseCase: ControllerUseCase,
) : ViewModel() {

    var info by mutableStateOf(FindViewState())
        private set

    fun fetchInfo() {
        udpUseCase.send().onEach {
            info = when (it) {
                is Resource.Loading -> info.copy(isLoading = it.isLoading)
                is Resource.Success -> info.copy(infoList = it.data ?: emptyList())
                is Resource.Error -> info.copy(message = it.message)
            }
        }.launchIn(viewModelScope)
    }

    fun addController(i: Info) {
        controllerUseCase
            .addController(i)
            .onCompletion {
                info = info.copy(message = it?.localizedMessage)
            }
            .launchIn(viewModelScope)
        fetchInfo()
    }

    fun removeController() {
        controllerUseCase
            .removeController()
            .onCompletion {
                info = info.copy(message = it?.localizedMessage)
            }
            .launchIn(viewModelScope)
    }

}