package ru.cityron.presentation.screens.metrics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.model.Chart
import ru.cityron.domain.repository.MetricRepository
import javax.inject.Inject

@HiltViewModel
class MetricsViewModel @Inject constructor(
    private val metricRepository: MetricRepository
) : ViewModel() {

    private val _chart = MutableStateFlow<Chart?>(null)
    val chart = _chart.asStateFlow()

    fun fetchChart(types: Int, sources: Int, values: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _chart.value = metricRepository.chart(types, sources, values)
        }
    }

}