package ru.cityron.presentation.screens.controller.metric

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.all.settings.metric.GetM3SettingsMetricUseCase
import ru.cityron.domain.utils.fromFrequencyToIndex
import ru.cityron.domain.utils.fromIndexToFrequency
import ru.cityron.domain.utils.toInt
import ru.cityron.domain.utils.utilsBitGet
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class ControllerMetricViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SettingsMetricUseCase: GetM3SettingsMetricUseCase
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
            val metric = getM3SettingsMetricUseCase()
            viewState = viewState.copy(
                valuesBitsOld = metric.valuesBits,
                valuesBits = metric.valuesBits,

                frequencyOld = metric.frequency.fromFrequencyToIndex(),
                frequency = metric.frequency.fromFrequencyToIndex(),

                capacity = getCapacity(metric.valuesBits)
            )
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
                    confRepository.conf("metric-frequency", viewState.frequency.fromIndexToFrequency())
                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = ControllerMetricViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}