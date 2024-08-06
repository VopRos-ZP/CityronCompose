package ru.cityron.presentation.screens.algo.timings

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3SettingsUseCase
import ru.cityron.presentation.mvi.MviViewModel
import javax.inject.Inject

@HiltViewModel
class AlgoTimingsViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SettingsUseCase: GetM3SettingsUseCase,
) : MviViewModel<AlgoTimingsViewState, AlgoTimingsViewIntent>() {

    override fun intent(intent: AlgoTimingsViewIntent) {
        when (intent) {
            is AlgoTimingsViewIntent.Launch -> launch()
            is AlgoTimingsViewIntent.OnTimeOpenDamperChange -> updateState {
                copy(
                    timeOpenDamper = intent.value,
                    isChanged = isChanged || timeOpenDamperOld != intent.value
                )
            }
            is AlgoTimingsViewIntent.OnTimeAccelerFanChange -> updateState {
                copy(
                    timeAccelerFan = intent.value,
                    isChanged = isChanged || timeAccelerFanOld != intent.value
                )
            }
            is AlgoTimingsViewIntent.OnTimeBlowHeatChange -> updateState {
                copy(
                    timeBlowHeat = intent.value,
                    isChanged = isChanged || timeBlowHeatOld != intent.value
                )
            }
            is AlgoTimingsViewIntent.OnIsChangedChange -> updateState { copy(isChanged = intent.value) }
            is AlgoTimingsViewIntent.OnSaveClick -> onSaveClick()
        }
    }

    private fun launch() {
        scope.launch {
            try {
                val settings = getM3SettingsUseCase()
                updateState(
                    { copy() }, AlgoTimingsViewState(
                        timeOpenDamperOld = settings.algo.timeOpenDamper,
                        timeOpenDamper = settings.algo.timeOpenDamper,
                        timeAccelerFanOld = settings.algo.timeAccelerFan,
                        timeAccelerFan = settings.algo.timeAccelerFan,
                        timeBlowHeatOld = settings.algo.timeBlowHeat,
                        timeBlowHeat = settings.algo.timeBlowHeat,
                    )
                )
            } catch (_: Exception) {}
        }
    }

    private fun onSaveClick() {
        scope.launch {
            state.value?.let {
                try {
                    confRepository.conf("algo-timeOpenDamper", it.timeOpenDamper)
                    confRepository.conf("algo-timeAccelerFan", it.timeAccelerFan)
                    confRepository.conf("algo-timeBlowHeat", it.timeBlowHeat)
                    updateState { copy(isChanged = false) }
                } catch (_: Exception) {
                    updateState { copy(isChanged = true) }
                }
            }
        }
    }

}
