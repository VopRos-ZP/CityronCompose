package ru.cityron.presentation.screens.events.filters

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.cityron.data.local.EventsStore
import ru.cityron.data.room.event.EventDatabase
import ru.cityron.data.room.event.EventDto
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class EventFiltersViewModel @Inject constructor(
    private val eventDatabase: EventDatabase,
) : BaseSharedViewModel<EventFiltersViewState, Any, EventFiltersViewIntent>(
    initialState = EventFiltersViewState(isChanged = true)
) {

    override fun intent(viewEvent: EventFiltersViewIntent) {
        when (viewEvent) {
            is EventFiltersViewIntent.Launch -> launch()
            is EventFiltersViewIntent.OnSaveClick -> onSaveClick()
            is EventFiltersViewIntent.OnCountChange -> onCountChange(viewEvent.value)
            is EventFiltersViewIntent.OnTypesChange -> onTypesChange(viewEvent.value)
            is EventFiltersViewIntent.OnSourcesChange -> onSourcesChange(viewEvent.value)
            is EventFiltersViewIntent.OnReasonsChange -> onReasonsChange(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            var dto = eventDatabase.dao.fetchAll().last()
            viewState = viewState.copy(
                id = dto.id,
                count = dto.count,
                types = dto.types,
                reasons = dto.reasons,
                sources = dto.sources
            )
            while (true) {
                dto = eventDatabase.dao.fetchAll().last()
                viewState = viewState.copy(
                    id = dto.id,
                    countOld = dto.count,
                    typesOld = dto.types,
                    reasonsOld = dto.reasons,
                    sourcesOld = dto.sources,
                )
                updateIsChanged()
                delay(1000)
            }
        }
    }

    private fun onCountChange(value: Int) {
        viewState = viewState.copy(count = value)
        updateIsChanged()
    }

    private fun onTypesChange(value: Int) {
        viewState = viewState.copy(types = value)
        updateIsChanged()
    }

    private fun onSourcesChange(value: Int) {
        viewState = viewState.copy(sources = value)
        updateIsChanged()
    }

    private fun onReasonsChange(value: Int) {
        viewState = viewState.copy(reasons = value)
        updateIsChanged()
    }

    private fun updateIsChanged() {
        viewState = viewState.copy(
            isChanged = viewState.count != viewState.countOld
                    || viewState.types != viewState.typesOld
                    || viewState.reasons != viewState.reasonsOld
                    || viewState.sources != viewState.sourcesOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope(viewModelScope) {
            val dto = EventDto(
                id = viewState.id,
                count = viewState.count,
                types = viewState.types,
                sources = viewState.sources,
                reasons = viewState.reasons
            )
            eventDatabase.dao.upsert(dto)
            updateIsChanged()
        }
    }

}