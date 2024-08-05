package ru.cityron.presentation.screens.controller.metric

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3AllUseCase
import ru.cityron.domain.utils.toInt
import ru.cityron.domain.utils.utilsBitGet
import ru.cityron.presentation.components.MviViewModel
import javax.inject.Inject

@HiltViewModel
class ControllerMetricViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AllUseCase: GetM3AllUseCase,
) : MviViewModel<ControllerMetricViewState, ControllerMetricViewIntent>() {

    override fun intent(intent: ControllerMetricViewIntent) {
        when (intent) {
            is ControllerMetricViewIntent.Launch -> launch()
            is ControllerMetricViewIntent.OnSaveClick -> onSaveClick()
            is ControllerMetricViewIntent.OnValuesBitsChange -> updateState {
                copy(
                    valuesBits = intent.value,
                    capacity = getCapacity(intent.value),
                    isChanged = isChanged || intent.value != valuesBitsOld,
                )
            }
            is ControllerMetricViewIntent.OnFrequencyChange -> updateState {
                copy(
                    frequency = intent.value,
                    isChanged = isChanged || intent.value != frequencyOld
                )
            }
        }
    }

    private fun launch() {
        updateState({ copy() }, ControllerMetricViewState())
        scope.launch {
            try {
                val settings = getM3AllUseCase().settings
                updateState {
                    copy(
                        valuesBitsOld = settings.metric.valuesBits,
                        valuesBits = settings.metric.valuesBits,
                        frequencyOld = frequencyToIndex(settings.metric.frequency),
                        frequency = frequencyToIndex(settings.metric.frequency),
                        capacity = getCapacity(settings.metric.valuesBits)
                    )
                }
            } catch (_: Exception) {}
        }
    }

    private fun frequencyToIndex(frequency: Int): Int {
        return when (frequency) {
            1 -> 0
            3 -> 1
            6 -> 2
            6 * 5 -> 3
            6 * 10 -> 4
            6 * 30 -> 5
            else -> 0
        }
    }

    private fun getCapacity(bits: Int): Int = when (bits) {
        in listOf(1, 2, 4) -> 62
        in listOf(3, 5, 6) -> 31
        else -> 21
    }

    private fun onSaveClick() {
        scope.launch {
            try {
                state.value?.let {
                    (0..2).forEach { n ->
                        confRepository.conf("metric-value$n", utilsBitGet(it.valuesBits, n).toInt())
                    }
                    confRepository.conf("metric-frequency", it.frequency)
                    updateState { copy(isChanged = false) }
                }
            } catch (_: Exception) {
                updateState { copy(isChanged = true) }
            }
        }
    }

}