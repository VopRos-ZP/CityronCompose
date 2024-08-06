package ru.cityron.presentation.screens.algo.other

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3SettingsUseCase
import ru.cityron.presentation.mvi.MviViewModel
import javax.inject.Inject

@HiltViewModel
class AlgoOtherViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SettingsUseCase: GetM3SettingsUseCase
) : MviViewModel<AlgoOtherViewState, AlgoOtherViewIntent>() {

    override fun intent(intent: AlgoOtherViewIntent) {
        when (intent) {
            is AlgoOtherViewIntent.Launch -> launch()
            is AlgoOtherViewIntent.OnSaveClick -> onSaveClick()
            is AlgoOtherViewIntent.OnTempControlChange -> updateState {
                copy(
                    tempControl = intent.value,
                    isChanged = isChanged || intent.value != tempControlOld
                )
            }
            is AlgoOtherViewIntent.OnFilterEnChange -> updateState {
                copy(
                    filterEn = intent.value,
                    isChanged = isChanged || intent.value != filterEnOld
                )
            }
            is AlgoOtherViewIntent.OnAutoStartEnChange -> updateState {
                copy(
                    autoStartEn = intent.value,
                    isChanged = isChanged || intent.value != autoStartEnOld
                )
            }
            is AlgoOtherViewIntent.OnIsDistPowerChange -> updateState {
                copy(
                    isDistPower = intent.value,
                    isChanged = isChanged || intent.value != isDistPowerOld
                )
            }
            is AlgoOtherViewIntent.OnAlarmRestartCountChange -> updateState {
                copy(
                    alarmRestartCount = intent.value,
                    isChanged = isChanged || intent.value != alarmRestartCountOld
                )
            }
        }
    }

    private fun launch() {
        scope.launch {
            try {
                val settings = getM3SettingsUseCase()
                updateState(
                    { copy() }, AlgoOtherViewState(
                        tempControlOld = settings.algo.tempControl,
                        tempControl = settings.algo.tempControl,

                        filterEnOld = settings.algo.filterEn,
                        filterEn = settings.algo.filterEn,

                        autoStartEnOld = settings.algo.autoStartEn,
                        autoStartEn = settings.algo.autoStartEn,

                        isDistPowerOld = settings.algo.isDistPower,
                        isDistPower = settings.algo.isDistPower,

                        alarmRestartCountOld = settings.algo.alarmRestartCount,
                        alarmRestartCount = settings.algo.alarmRestartCount,
                    )
                )
            } catch (_: Exception) {}
        }
    }

    private fun onSaveClick() {
        scope.launch {
            state.value?.let {
                try {
                    confRepository.conf("algo-tempControl", it.tempControl)
                    confRepository.conf("algo-filterEn", it.filterEn)
                    confRepository.conf("algo-autoStartEn", it.autoStartEn)
                    confRepository.conf("algo-isDistPower", it.isDistPower)
                    confRepository.conf("algo-alarmRestartCount", it.alarmRestartCount)

                    updateState { copy(isChanged = false) }
                } catch (_: Exception) {
                    updateState { copy(isChanged = true) }
                }
            }
        }
    }

}