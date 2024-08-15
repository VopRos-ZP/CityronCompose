package ru.cityron.presentation.screens.algo.other

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.algo.other.GetAlgoOtherUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoOtherViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getAlgoOtherUseCase: GetAlgoOtherUseCase,
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
        withViewModelScope {
            val algo = getAlgoOtherUseCase()
            viewState = viewState.copy(
                tempControlOld = algo.tempControl,
                tempControl = algo.tempControl,

                filterEnOld = algo.filterEn,
                filterEn = algo.filterEn,

                autoStartEnOld = algo.autoStartEn,
                autoStartEn = algo.autoStartEn,

                isDistPowerOld = algo.isDistPower,
                isDistPower = algo.isDistPower,

                alarmRestartCountOld = algo.alarmRestartCount,
                alarmRestartCount = algo.alarmRestartCount,
                alarmRestartCountRange = (algo.alarmRestartCountMin..algo.alarmRestartCountMax)
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onTempControlChange(value: Int) {
        viewState = viewState.copy(tempControl = value)
        updateIsChanged()
    }

    private fun onFilterEnChange(value: Int) {
        viewState = viewState.copy(filterEn = value)
        updateIsChanged()
    }

    private fun onAutoStartEnChange(value: Int) {
        viewState = viewState.copy(autoStartEn = value)
        updateIsChanged()
    }

    private fun onIsDistPowerChange(value: Int) {
        viewState = viewState.copy(isDistPower = value)
        updateIsChanged()
    }

    private fun onAlarmRestartCountChange(value: Int) {
        viewState = viewState.copy(
            alarmRestartCount = value,
            alarmRestartCountInRange = value in viewState.alarmRestartCountRange,
        )
        updateIsChanged()
    }

    private fun updateIsChanged() {
        viewState = viewState.copy(
            isChanged = viewState.alarmRestartCount != viewState.alarmRestartCountOld
                    || viewState.filterEn != viewState.filterEnOld
                    || viewState.autoStartEn != viewState.autoStartEnOld
                    || viewState.isDistPower != viewState.isDistPowerOld
                    || viewState.tempControl != viewState.tempControlOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                if (viewState.tempControl != viewState.tempControlOld)
                    confRepository.conf("algo-tempControl", viewState.tempControl)

                if (viewState.filterEn != viewState.filterEnOld)
                    confRepository.conf("algo-filterEn", viewState.filterEn)

                if (viewState.autoStartEn != viewState.autoStartEnOld)
                    confRepository.conf("algo-autoStartEn", viewState.autoStartEn)

                if (viewState.isDistPower != viewState.isDistPowerOld)
                    confRepository.conf("algo-isDistPower", viewState.isDistPower)

                if (viewState.alarmRestartCount != viewState.alarmRestartCountOld)
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