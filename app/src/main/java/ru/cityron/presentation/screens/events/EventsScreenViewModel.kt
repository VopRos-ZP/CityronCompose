package ru.cityron.presentation.screens.events

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.usecase.events.GetEventsUseCase
import ru.cityron.domain.usecase.events.GetFiltersUseCase
import ru.cityron.domain.utils.Filters
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class EventsScreenViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val getFiltersUseCase: GetFiltersUseCase,
) : BaseSharedViewModel<EventsViewState, Any, EventsViewIntent>(
    initialState = EventsViewState()
) {

    init {
        withViewModelScope {
            getFiltersUseCase.flow.collect {
                viewState = viewState.copy(
                    count = it.count,
                    types = it.types,
                    reasons = it.reasons,
                    sources = it.sources
                )
            }
        }
    }

    override fun intent(viewEvent: EventsViewIntent) {
        when (viewEvent) {
            is EventsViewIntent.Launch -> launch()
        }
    }

    private fun launch() {
        withViewModelScope {
            setupIsFiltered()
            getEventsUseCase.flow.collect {
                viewState = viewState.copy(events = it)
            }
        }
    }

    private fun setupIsFiltered() {
        viewState = viewState.copy(
            isFiltered = Filters.COUNT != viewState.count
                    || Filters.TYPES != viewState.types
                    || Filters.REASONS != viewState.reasons
                    || Filters.SOURCES != viewState.sources
        )
    }

}