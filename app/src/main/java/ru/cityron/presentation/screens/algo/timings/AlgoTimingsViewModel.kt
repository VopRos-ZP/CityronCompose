package ru.cityron.presentation.screens.algo.timings

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.algo.timing.GetAlgoTimingUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoTimingsViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getAlgoTimingUseCase: GetAlgoTimingUseCase,
) : BaseSharedViewModel<AlgoTimingsViewState, AlgoTimingsViewAction, AlgoTimingsViewIntent>(
    initialState = AlgoTimingsViewState()
) {

    override fun intent(viewEvent: AlgoTimingsViewIntent) {
        when (viewEvent) {
            is AlgoTimingsViewIntent.Launch -> launch()
            is AlgoTimingsViewIntent.OnSaveClick -> onSaveClick()
            is AlgoTimingsViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is AlgoTimingsViewIntent.OnTimeOpenDamperChange -> onTimeOpenDamperChange(viewEvent.value)
            is AlgoTimingsViewIntent.OnTimeAccelerFanChange -> onTimeAccelerFanChange(viewEvent.value)
            is AlgoTimingsViewIntent.OnTimeBlowHeatChange -> onTimeBlowHeatChange(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            val algo = getAlgoTimingUseCase()
            viewState = viewState.copy(
                timeOpenDamperOld = algo.timeOpenDamper,
                timeOpenDamper = algo.timeOpenDamper,
                timeOpenDamperRange = (algo.timeOpenDamperMin..algo.timeOpenDamperMax),

                timeAccelerFanOld = algo.timeAccelerFan,
                timeAccelerFan = algo.timeAccelerFan,
                timeAccelerFanRange = (algo.timeAccelerFanMin..algo.timeAccelerFanMax),

                timeBlowHeatOld = algo.timeBlowHeat,
                timeBlowHeat = algo.timeBlowHeat,
                timeBlowHeatRange = (algo.timeBlowHeatMin..algo.timeBlowHeatMax),
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onTimeOpenDamperChange(value: Int) {
        viewState = viewState.copy(
            timeOpenDamper = value,
            timeOpenDamperInRange = value in viewState.timeOpenDamperRange,
        )
        updateIsChanged()
    }

    private fun onTimeAccelerFanChange(value: Int) {
        viewState = viewState.copy(
            timeAccelerFan = value,
            timeAccelerFanInRange = value in viewState.timeAccelerFanRange,
        )
        updateIsChanged()
    }

    private fun onTimeBlowHeatChange(value: Int) {
        viewState = viewState.copy(
            timeBlowHeat = value,
            timeBlowHeatInRange = value in viewState.timeBlowHeatRange,
        )
        updateIsChanged()
    }

    private fun updateIsChanged() {
        viewState = viewState.copy(
            isChanged = viewState.timeOpenDamper != viewState.timeOpenDamperOld
                    || viewState.timeBlowHeat != viewState.timeBlowHeatOld
                    || viewState.timeAccelerFan != viewState.timeAccelerFanOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                if (viewState.timeOpenDamper != viewState.timeOpenDamperOld)
                    confRepository.conf("algo-timeOpenDamper", viewState.timeOpenDamper)

                if (viewState.timeAccelerFan != viewState.timeAccelerFanOld)
                    confRepository.conf("algo-timeAccelerFan", viewState.timeAccelerFan)

                if (viewState.timeBlowHeat != viewState.timeBlowHeatOld)
                    confRepository.conf("algo-timeBlowHeat", viewState.timeBlowHeat)

                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = AlgoTimingsViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}
