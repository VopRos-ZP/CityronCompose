package com.vopros.cityron.ui.screens.events

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.vopros.cityron.ui.ConfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.cityron.core.data.usecase.EventUseCase
import ru.cityron.core.domain.repository.ConfRepository
import ru.cityron.core.domain.utils.Resource
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    confRepository: ConfRepository,
    private val eventsUseCase: EventUseCase,
) : ConfViewModel(confRepository) {

    var state by mutableStateOf(EventsViewState())
        private set

    fun fetchEvents(
        count: Int,
        types: Int,
        sources: Int,
        reasons: Int
    ) {
        eventsUseCase.fetchEvents(count, types, sources, reasons).onEach {
            state = when (it) {
                is Resource.Loading -> state.copy(isLoading = it.isLoading)
                is Resource.Success -> state.copy(events = it.data ?: emptyList())
                is Resource.Error -> state.copy(message = it.message)
            }
        }.launchIn(viewModelScope)
    }

}