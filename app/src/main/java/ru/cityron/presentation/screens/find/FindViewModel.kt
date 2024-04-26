package ru.cityron.presentation.screens.find

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.model.Controller
import ru.cityron.domain.usecase.GetInfoListUseCase
import ru.cityron.domain.usecase.UpsertControllerUseCase
import ru.cityron.domain.utils.toController
import javax.inject.Inject

@HiltViewModel
class FindViewModel @Inject constructor(
    private val getInfoListUseCase: GetInfoListUseCase,
    private val controllerUseCase: UpsertControllerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Map<Controller, Boolean>>(emptyMap())
    val infoList = _state.asStateFlow()

    fun fetchInfoList() {
        viewModelScope.launch {
            getInfoListUseCase.infoList.collect { map ->
                _state.emit(map.mapKeys { it.key.toController() })
            }
        }
    }

    fun addController(controller: Controller) {
        viewModelScope.launch {
            controllerUseCase(controller)
        }
    }

}