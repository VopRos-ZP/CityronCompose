package ru.cityron.presentation.screens.m3tabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.M3Repository
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class M3ViewModel @Inject constructor(
    private val m3Repository: M3Repository
) : ViewModel() {

    private val _state = MutableStateFlow(M3TabsState())
    val state = _state.asStateFlow()

    fun fetchState() {
        viewModelScope.launch {
            try {
                m3Repository.state.collect {
                    _state.value = state.value.copy(state = it)
                }
            } catch (_: SocketTimeoutException) {
                _state.value = state.value.copy(error = "Нет связи")
            }
        }
    }

}