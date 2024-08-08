package ru.cityron.presentation.screens.events

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.cityron.data.local.EventsStore
import ru.cityron.data.room.event.EventDatabase
import ru.cityron.domain.repository.EventsRepository
import ru.cityron.domain.utils.Filters
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class EventsScreenViewModel @Inject constructor(
    private val eventsDatabase: EventDatabase,
    private val eventsRepository: EventsRepository
) : BaseSharedViewModel<EventsViewState, Any, EventsViewIntent>(
    initialState = EventsViewState()
) {

    init {
        withViewModelScope {
            while (true) {
                val dto = eventsDatabase.dao.fetchAll().last()
                viewState = viewState.copy(
                    count = dto.count,
                    types = dto.types,
                    reasons = dto.reasons,
                    sources = dto.sources
                )
                delay(1000)
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
            val events = eventsRepository.fetchEvents(
                count = when (viewState.count) {
                    1 -> 50
                    2 -> 100
                    3 -> 500
                    4 -> 1000
                    5 -> 1500
                    else -> -1
                },
                types = indexToValue(viewState.types),
                sources = indexToValue(viewState.sources),
                reasons = indexToValue(viewState.reasons)
            )
            viewState = viewState.copy(events = events)
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

    private fun indexToValue(i: Int): Int = when (i) {
        0 -> -1
        else -> 2f.pow(i - 1).toInt()
    }

}