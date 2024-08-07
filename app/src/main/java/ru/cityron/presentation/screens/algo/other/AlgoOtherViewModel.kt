package ru.cityron.presentation.screens.algo.other

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3AllUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoOtherViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AllUseCase: GetM3AllUseCase,
) : BaseSharedViewModel<AlgoOtherViewState, AlgoOtherViewAction, AlgoOtherViewIntent>(
    initialState = AlgoOtherViewState()
) {

    override fun intent(viewEvent: AlgoOtherViewIntent) {
        when (viewEvent) {
            is AlgoOtherViewIntent.Launch -> launch()
            is AlgoOtherViewIntent.OnSaveClick -> onSaveClick()
            is AlgoOtherViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is AlgoOtherViewIntent.OnTempControlChange -> onTempControlChange(viewEvent.value)
            is AlgoOtherViewIntent.OnFilterEnChange -> onFilterEnChange(viewEvent.value)
            is AlgoOtherViewIntent.OnAutoStartEnChange -> onAutoStartEnChange(viewEvent.value)
            is AlgoOtherViewIntent.OnIsDistPowerChange -> onIsDistPowerChange(viewEvent.value)
            is AlgoOtherViewIntent.OnAlarmRestartCountChange -> onAlarmRestartCountChange(viewEvent.value)
        }
    }

    private fun launch() {
        scope.launch {
            val all = getM3AllUseCase()
            viewState = viewState.copy(
                tempControlOld = all.settings.algo.tempControl,
                tempControl = all.settings.algo.tempControl,

                filterEnOld = all.settings.algo.filterEn,
                filterEn = all.settings.algo.filterEn,

                autoStartEnOld = all.settings.algo.autoStartEn,
                autoStartEn = all.settings.algo.autoStartEn,

                isDistPowerOld = all.settings.algo.isDistPower,
                isDistPower = all.settings.algo.isDistPower,

                alarmRestartCountOld = all.settings.algo.alarmRestartCount,
                alarmRestartCount = all.settings.algo.alarmRestartCount,
                alarmRestartCountRange = (all.static.settingsMin.algo.alarmRestartCount..all.static.settingsMax.algo.alarmRestartCount)
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onTempControlChange(value: Int) {
        viewState = viewState.copy(
            tempControl = value,
            isChanged = value != viewState.tempControlOld
                    || viewState.filterEn != viewState.filterEnOld
                    || viewState.autoStartEn != viewState.autoStartEnOld
                    || viewState.isDistPower != viewState.isDistPowerOld
                    || viewState.alarmRestartCount != viewState.alarmRestartCountOld
        )
    }

    private fun onFilterEnChange(value: Int) {
        viewState = viewState.copy(
            filterEn = value,
            isChanged = value != viewState.filterEnOld
                    || viewState.tempControl != viewState.tempControlOld
                    || viewState.autoStartEn != viewState.autoStartEnOld
                    || viewState.isDistPower != viewState.isDistPowerOld
                    || viewState.alarmRestartCount != viewState.alarmRestartCountOld
        )
    }

    private fun onAutoStartEnChange(value: Int) {
        viewState = viewState.copy(
            autoStartEn = value,
            isChanged = value != viewState.autoStartEnOld
                    || viewState.filterEn != viewState.filterEnOld
                    || viewState.tempControl != viewState.tempControlOld
                    || viewState.isDistPower != viewState.isDistPowerOld
                    || viewState.alarmRestartCount != viewState.alarmRestartCountOld
        )
    }

    private fun onIsDistPowerChange(value: Int) {
        viewState = viewState.copy(
            isDistPower = value,
            isChanged = value != viewState.isDistPowerOld
                    || viewState.filterEn != viewState.filterEnOld
                    || viewState.autoStartEn != viewState.autoStartEnOld
                    || viewState.tempControl != viewState.tempControlOld
                    || viewState.alarmRestartCount != viewState.alarmRestartCountOld
        )
    }

    private fun onAlarmRestartCountChange(value: Int) {
        viewState = viewState.copy(
            alarmRestartCount = value,
            alarmRestartCountInRange = value in viewState.alarmRestartCountRange,
            isChanged = value != viewState.alarmRestartCountOld
                    || viewState.filterEn != viewState.filterEnOld
                    || viewState.autoStartEn != viewState.autoStartEnOld
                    || viewState.isDistPower != viewState.isDistPowerOld
                    || viewState.tempControl != viewState.tempControlOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                confRepository.conf("algo-tempControl", viewState.tempControl)
                confRepository.conf("algo-filterEn", viewState.filterEn)
                confRepository.conf("algo-autoStartEn", viewState.autoStartEn)
                confRepository.conf("algo-isDistPower", viewState.isDistPower)
                confRepository.conf("algo-alarmRestartCount", viewState.alarmRestartCount)
                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = AlgoOtherViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}