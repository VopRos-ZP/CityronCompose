package ru.cityron.presentation.screens.controller.metric

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3AllUseCase
import ru.cityron.domain.utils.toInt
import ru.cityron.domain.utils.utilsBitGet
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class ControllerMetricViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AllUseCase: GetM3AllUseCase,
) : BaseSharedViewModel<ControllerMetricViewState, ControllerMetricViewAction, ControllerMetricViewIntent>(
    initialState = ControllerMetricViewState()
) {

    override fun intent(viewEvent: ControllerMetricViewIntent) {
        when (viewEvent) {
            is ControllerMetricViewIntent.Launch -> launch()
            is ControllerMetricViewIntent.OnSaveClick -> onSaveClick()
            is ControllerMetricViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is ControllerMetricViewIntent.OnValuesBitsChange -> onValuesBitsChange(viewEvent.value)
            is ControllerMetricViewIntent.OnFrequencyChange -> onFrequencyChange(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            val settings = getM3AllUseCase().settings
            viewState = viewState.copy(
                valuesBitsOld = settings.metric.valuesBits,
                valuesBits = settings.metric.valuesBits,

                frequencyOld = frequencyToIndex(settings.metric.frequency),
                frequency = frequencyToIndex(settings.metric.frequency),

                capacity = getCapacity(settings.metric.valuesBits)
            )
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
        7 -> 21
        else -> 62
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onValuesBitsChange(value: Int) {
        viewState = viewState.copy(
            valuesBits = value,
            capacity = getCapacity(value),
            isChanged = value != viewState.valuesBitsOld || viewState.frequency != viewState.frequencyOld
        )
    }

    private fun onFrequencyChange(value: Int) {
        viewState = viewState.copy(
            frequency = value,
            isChanged = value != viewState.frequencyOld || viewState.valuesBits != viewState.valuesBitsOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                if (viewState.valuesBits != viewState.valuesBitsOld) {
                    (0..2).forEach { n ->
                        confRepository.conf(
                            "metric-value$n",
                            utilsBitGet(viewState.valuesBits, n).toInt()
                        )
                    }
                }
                if (viewState.frequency != viewState.frequencyOld)
                    confRepository.conf("metric-frequency", viewState.frequency)
                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = ControllerMetricViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}