package ru.cityron.presentation.screens.metrics

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.repository.MetricRepository
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class MetricsViewModel @Inject constructor(
    private val metricRepository: MetricRepository
) : BaseSharedViewModel<MetricsViewState, Any, MetricsViewIntent>(
    initialState = MetricsViewState()
) {

    override fun intent(viewEvent: MetricsViewIntent) {
        when (viewEvent) {
            is MetricsViewIntent.FetchChart -> fetchChart(
                viewEvent.start, viewEvent.end, viewEvent.types, viewEvent.sources, viewEvent.values
            )
            is MetricsViewIntent.OnStartChange -> viewState = viewState.copy(start = viewEvent.value)
            is MetricsViewIntent.OnEndChange -> viewState = viewState.copy(end = viewEvent.value)
        }
    }

    private fun fetchChart(start: Long, end: Long, types: Int, sources: Int, values: Int) {
        withViewModelScope {
            viewState = viewState.copy(chart = metricRepository.chart(start, end, types, sources, values))
        }
    }

}