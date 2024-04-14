package com.vopros.cityron.m3.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.vopros.cityron.ui.ConfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.cityron.core.domain.repository.ConfRepository
import ru.cityron.core.domain.utils.Resource
import ru.cityron.m3.data.usecase.M3UseCase
import javax.inject.Inject

@HiltViewModel
class M3ViewModel @Inject constructor(
    confRepository: ConfRepository,
    private val m3UseCase: M3UseCase
) : ConfViewModel(confRepository) {

    var state by mutableStateOf(M3ViewState())
        private set

    fun fetchAll() {
        m3UseCase.fetchAll().onEach {
            state = when (it) {
                is Resource.Loading -> state.copy(isLoading = it.isLoading)
                is Resource.Success -> state.copy(all = it.data)
                is Resource.Error -> state.copy(message = it.message)
            }
        }.launchIn(viewModelScope)
    }

    fun stopFetching() {
        m3UseCase.stopFetching()
    }

}