package ru.cityron.presentation.screens.algo.electric

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3SettingsUseCase
import ru.cityron.presentation.mvi.MviViewModel
import javax.inject.Inject

@HiltViewModel
class AlgoElectricViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SettingsUseCase: GetM3SettingsUseCase
) : MviViewModel<AlgoElectricViewState, AlgoElectricViewIntent>() {

    override fun intent(intent: AlgoElectricViewIntent) {
        when (intent) {
            is AlgoElectricViewIntent.Launch -> launch()
            is AlgoElectricViewIntent.OnSaveClick -> onSaveClick()
            is AlgoElectricViewIntent.OnHeatPwmPeriodChange -> updateState {
                copy(
                    heatPwmPeriod = intent.value,
                    isChanged = intent.value != heatPwmPeriodOld
                )
            }
        }
    }

    private fun launch() {
        scope.launch {
            try {
                val settings = getM3SettingsUseCase()
                updateState({ copy() }, AlgoElectricViewState(
                    heatPwmPeriodOld = settings.algo.heatPwmPeriod,
                    heatPwmPeriod = settings.algo.heatPwmPeriod,
                ))
            } catch (_: Exception) {}
        }
    }

    private fun onSaveClick() {
        scope.launch {
            state.value?.let {
                try {
                    confRepository.conf("algo-heatPwmPeriod", it.heatPwmPeriod)
                    updateState { copy(isChanged = false) }
                } catch (_: Exception) {
                    updateState { copy(isChanged = true) }
                }
            }
        }
    }

}