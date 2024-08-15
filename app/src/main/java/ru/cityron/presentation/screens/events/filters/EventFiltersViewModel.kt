package ru.cityron.presentation.screens.events.filters

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.model.Filter
import ru.cityron.domain.usecase.events.GetFiltersUseCase
import ru.cityron.domain.usecase.events.UpsertFilterUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class EventFiltersViewModel @Inject constructor(
    private val getFiltersUseCase: GetFiltersUseCase,
    private val upsertFilterUseCase: UpsertFilterUseCase,
) : BaseSharedViewModel<EventFiltersViewState, Any, EventFiltersViewIntent>(
    initialState = EventFiltersViewState()
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
            getFiltersUseCase.flow.collect { filter ->
                viewState = viewState.copy(
                    countOld = filter.count,
                    count = filter.count,
                    typesOld = filter.types,
                    types = filter.types,
                    reasonsOld = filter.reasons,
                    reasons = filter.reasons,
                    sourcesOld = filter.sources,
                    sources = filter.sources,
                )
                updateIsChanged()
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
        withViewModelScope {
            upsertFilterUseCase(
                Filter(
                    count = viewState.count,
                    types = viewState.types,
                    sources = viewState.sources,
                    reasons = viewState.reasons
                )
            )
        }
    }

}