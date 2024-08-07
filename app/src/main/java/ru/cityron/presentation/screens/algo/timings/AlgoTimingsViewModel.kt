package ru.cityron.presentation.screens.algo.timings

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3AllUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoTimingsViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AllUseCase: GetM3AllUseCase,
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
            val all = getM3AllUseCase()
            viewState = viewState.copy(
                timeOpenDamperOld = all.settings.algo.timeOpenDamper,
                timeOpenDamper = all.settings.algo.timeOpenDamper,
                timeOpenDamperRange = (all.static.settingsMin.algo.timeOpenDamper..all.static.settingsMax.algo.timeOpenDamper),

                timeAccelerFanOld = all.settings.algo.timeAccelerFan,
                timeAccelerFan = all.settings.algo.timeAccelerFan,
                timeAccelerFanRange = (all.static.settingsMin.algo.timeAccelerFan..all.static.settingsMax.algo.timeAccelerFan),

                timeBlowHeatOld = all.settings.algo.timeBlowHeat,
                timeBlowHeat = all.settings.algo.timeBlowHeat,
                timeBlowHeatRange = (all.static.settingsMin.algo.timeBlowHeat..all.static.settingsMax.algo.timeBlowHeat),
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
            isChanged = value != viewState.timeOpenDamperOld
                    || viewState.timeBlowHeat != viewState.timeBlowHeatOld
                    || viewState.timeAccelerFan != viewState.timeAccelerFanOld
        )
    }

    private fun onTimeAccelerFanChange(value: Int) {
        viewState = viewState.copy(
            timeAccelerFan = value,
            timeAccelerFanInRange = value in viewState.timeAccelerFanRange,
            isChanged = value != viewState.timeAccelerFanOld
                    || viewState.timeOpenDamper != viewState.timeOpenDamperOld
                    || viewState.timeBlowHeat != viewState.timeBlowHeatOld
        )
    }

    private fun onTimeBlowHeatChange(value: Int) {
        viewState = viewState.copy(
            timeBlowHeat = value,
            timeBlowHeatInRange = value in viewState.timeBlowHeatRange,
            isChanged = value != viewState.timeBlowHeatOld
                    || viewState.timeOpenDamper != viewState.timeOpenDamperOld
                    || viewState.timeAccelerFan != viewState.timeAccelerFanOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                confRepository.conf("algo-timeOpenDamper", viewState.timeOpenDamper)
                confRepository.conf("algo-timeAccelerFan", viewState.timeAccelerFan)
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
