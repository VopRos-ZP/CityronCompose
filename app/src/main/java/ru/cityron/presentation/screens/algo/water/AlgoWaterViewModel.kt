package ru.cityron.presentation.screens.algo.water

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3SettingsUseCase
import ru.cityron.presentation.components.MviViewModel
import javax.inject.Inject

@HiltViewModel
class AlgoWaterViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SettingsUseCase: GetM3SettingsUseCase
) : MviViewModel<AlgoWaterViewState, AlgoWaterViewIntent>() {

    override fun intent(intent: AlgoWaterViewIntent) {
        when (intent) {
            is AlgoWaterViewIntent.Launch -> launch()
            is AlgoWaterViewIntent.OnSaveClick -> onSaveClick()
            is AlgoWaterViewIntent.OnModeZimaLetoSourceChange -> updateState {
                copy(
                    modeZimaLetoSource = intent.value,
                    isChanged = isChanged || intent.value != modeZimaLetoSourceOld
                )
            }
            is AlgoWaterViewIntent.OnModeZimaLetoUserChange -> updateState {
                copy(
                    modeZimaLetoUser = intent.value,
                    isChanged = isChanged || intent.value != modeZimaLetoUserOld
                )
            }
            is AlgoWaterViewIntent.OnTimeWarmUpChange -> updateState {
                copy(
                    timeWarmUp = intent.value,
                    isChanged = isChanged || intent.value != timeWarmUpOld
                )
            }
            is AlgoWaterViewIntent.OnTimeDefrostChange -> updateState {
                copy(
                    timeDefrost = intent.value,
                    isChanged = isChanged || intent.value != timeDefrostOld
                )
            }
        }
    }

    private fun launch() {
        scope.launch {
            try {
                val settings = getM3SettingsUseCase()
                updateState({ copy() }, AlgoWaterViewState(
                    modeZimaLetoSourceOld = settings.algo.modeZimaLetoSource,
                    modeZimaLetoSource = settings.algo.modeZimaLetoSource,

                    modeZimaLetoUserOld = settings.algo.modeZimaLetoUser,
                    modeZimaLetoUser = settings.algo.modeZimaLetoUser,

                    timeWarmUpOld = settings.algo.timeWarmUp,
                    timeWarmUp = settings.algo.timeWarmUp,

                    timeDefrostOld = settings.algo.timeDefrost,
                    timeDefrost = settings.algo.timeDefrost,
                ))
            } catch (_: Exception) {}
        }
    }

    private fun onSaveClick() {
        scope.launch {
            state.value?.let {
                try {
                    confRepository.conf("algo-modeZimaLetoSource", it.modeZimaLetoSource)
                    confRepository.conf("algo-modeZimaLetoUser", it.modeZimaLetoUser)
                    confRepository.conf("algo-timeWarmUp", it.timeWarmUp)
                    confRepository.conf("algo-timeDefrost", it.timeDefrost)
                    updateState { copy(isChanged = false) }
                } catch (_: Exception) {
                    updateState { copy(isChanged = true) }
                }
            }
        }
    }

}