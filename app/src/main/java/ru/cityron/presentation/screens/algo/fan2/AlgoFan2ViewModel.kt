package ru.cityron.presentation.screens.algo.fan2

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3SettingsUseCase
import ru.cityron.presentation.components.MviViewModel
import javax.inject.Inject

@HiltViewModel
class AlgoFan2ViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SettingsUseCase: GetM3SettingsUseCase
) : MviViewModel<AlgoFan2ViewState, AlgoFan2ViewIntent>() {

    override fun intent(intent: AlgoFan2ViewIntent) {
        when (intent) {
            is AlgoFan2ViewIntent.Launch -> launch()
            is AlgoFan2ViewIntent.OnSpeedMinChange -> updateState {
                copy(
                    fan2SpeedMin = intent.value,
                    isChanged = intent.value != fan2SpeedMinOld
                )
            }
            is AlgoFan2ViewIntent.OnSpeedMaxChange -> updateState {
                copy(
                    fan2SpeedMax = intent.value,
                    isChanged = intent.value != fan2SpeedMaxOld
                )
            }
            is AlgoFan2ViewIntent.OnSaveClick -> onSaveClick()
        }
    }

    private fun launch() {
        scope.launch {
            try {
                val settings = getM3SettingsUseCase()
                updateState(
                    { copy() }, AlgoFan2ViewState(
                        fan2SpeedMinOld = settings.algo.fan1SpeedMin,
                        fan2SpeedMin = settings.algo.fan1SpeedMin,
                        fan2SpeedMaxOld = settings.algo.fan1SpeedMax,
                        fan2SpeedMax = settings.algo.fan1SpeedMax,
                    )
                )
            } catch (_: Exception) {}
        }
    }

    private fun onSaveClick() {
        scope.launch {
            state.value?.let {
                try {
                    confRepository.conf("algo-fan2SpeedMin", it.fan2SpeedMin)
                    confRepository.conf("algo-fan2SpeedMax", it.fan2SpeedMax)
                    updateState { copy(isChanged = false) }
                } catch (_: Exception) {
                    updateState { copy(isChanged = true) }
                }
            }
        }
    }

}