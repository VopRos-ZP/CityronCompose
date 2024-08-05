package ru.cityron.presentation.screens.algo.fan1

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3SettingsUseCase
import ru.cityron.presentation.components.MviViewModel
import javax.inject.Inject

@HiltViewModel
class AlgoFan1ViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SettingsUseCase: GetM3SettingsUseCase
) : MviViewModel<AlgoFan1ViewState, AlgoFan1ViewIntent>() {

    override fun intent(intent: AlgoFan1ViewIntent) {
        when (intent) {
            is AlgoFan1ViewIntent.Launch -> launch()
            is AlgoFan1ViewIntent.OnSpeedMinChange -> updateState {
                copy(
                    fan1SpeedMin = intent.value,
                    isChanged = isChanged ||intent.value != fan1SpeedMinOld
                )
            }
            is AlgoFan1ViewIntent.OnSpeedMaxChange -> updateState {
                copy(
                    fan1SpeedMax = intent.value,
                    isChanged = isChanged ||intent.value != fan1SpeedMaxOld
                )
            }
            is AlgoFan1ViewIntent.OnSaveClick -> onSaveClick()
        }
    }

    private fun launch() {
        scope.launch {
            try {
                val settings = getM3SettingsUseCase()
                updateState(
                    { copy() }, AlgoFan1ViewState(
                        fan1SpeedMinOld = settings.algo.fan1SpeedMin,
                        fan1SpeedMin = settings.algo.fan1SpeedMin,
                        fan1SpeedMaxOld = settings.algo.fan1SpeedMax,
                        fan1SpeedMax = settings.algo.fan1SpeedMax,
                    )
                )
            } catch (_: Exception) {}
        }
    }

    private fun onSaveClick() {
        scope.launch {
            state.value?.let {
                try {
                    confRepository.conf("algo-fan1SpeedMin", it.fan1SpeedMin)
                    confRepository.conf("algo-fan1SpeedMax", it.fan1SpeedMax)
                    updateState { copy(isChanged = false) }
                } catch (_: Exception) {
                    updateState { copy(isChanged = true) }
                }
            }
        }
    }

}