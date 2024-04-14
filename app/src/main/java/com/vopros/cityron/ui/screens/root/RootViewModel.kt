package com.vopros.cityron.ui.screens.root

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.cityron.core.data.usecase.ControllerUseCase
import ru.cityron.core.domain.model.Controller
import ru.cityron.core.domain.repository.CControllerRepository
import ru.cityron.core.domain.utils.Resource
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val controllerUseCase: ControllerUseCase,
    private val cControllerRepository: CControllerRepository
) : ViewModel() {

    var state by mutableStateOf(RootViewState())
        private set

    val controller = cControllerRepository.controller

    fun fetchControllers() {
        controllerUseCase.fetchAll().onEach {
            state = when (it) {
                is Resource.Loading -> state.copy(isLoading = it.isLoading)
                is Resource.Success -> state.copy(controllers = it.data ?: emptyList())
                is Resource.Error -> state.copy(message = it.message)
            }
        }.launchIn(viewModelScope)
    }

    fun setController(controller: Controller) {
        cControllerRepository.controller = controller
    }

}
