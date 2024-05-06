package ru.cityron.presentation.screens.metrics

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.repository.MetricRepository
import javax.inject.Inject

@HiltViewModel
class MetricsViewModel @Inject constructor(
    private val metricRepository: MetricRepository
) : ViewModel() {



}