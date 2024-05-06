package ru.cityron.presentation.screens.m3tabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.repository.M3Repository
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class M3ViewModel @Inject constructor(
    private val currentRepository: CurrentRepository,
    private val m3Repository: M3Repository
) : ViewModel() {

    private val _controller = MutableStateFlow<Pair<Controller, DataSource>?>(null)
    val controller = _controller.asStateFlow()

    private val _state = MutableStateFlow<M3TabsState>(M3TabsState.Loading)
    val state = _state.asStateFlow()

    fun fetchState() {
        _controller.value = currentRepository.current
    }

    fun closeError() {

    }

}