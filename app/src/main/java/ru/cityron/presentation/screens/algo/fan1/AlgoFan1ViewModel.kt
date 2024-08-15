package ru.cityron.presentation.screens.algo.fan1

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.algo.fan.GetAlgoFanUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoFan1ViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getAlgoFanUseCase: GetAlgoFanUseCase,
) : BaseSharedViewModel<AlgoFan1ViewState, AlgoFan1ViewAction, AlgoFan1ViewIntent>(
    initialState = AlgoFan1ViewState()
) {

    override fun intent(viewEvent: AlgoFan1ViewIntent) {
        when (viewEvent) {
            is AlgoFan1ViewIntent.Launch -> launch()
            is AlgoFan1ViewIntent.OnSaveClick -> onSaveClick()
            is AlgoFan1ViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is AlgoFan1ViewIntent.OnSpeedMinChange -> onSpeedMinChange(viewEvent.value)
            is AlgoFan1ViewIntent.OnSpeedMaxChange -> onSpeedMaxChange(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            val algo = getAlgoFanUseCase(1)
            viewState = viewState.copy(
                fan1SpeedMinOld = algo.fanSpeedMin,
                fan1SpeedMin = algo.fanSpeedMin,
                fan1SpeedMinMin = algo.fanSpeedMinMin,
                fan1SpeedMinMax = algo.fanSpeedMinMax,

                fan1SpeedMaxOld = algo.fanSpeedMax,
                fan1SpeedMax = algo.fanSpeedMax,
                fan1SpeedMaxMin = algo.fanSpeedMaxMin,
                fan1SpeedMaxMax = algo.fanSpeedMaxMax,
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onSpeedMinChange(value: Int) {
        viewState = viewState.copy(
            fan1SpeedMin = value,
            fan1SpeedMinInRange = value in (viewState.fan1SpeedMinMin..viewState.fan1SpeedMinMax),
            isChanged = value != viewState.fan1SpeedMinOld || viewState.fan1SpeedMax != viewState.fan1SpeedMaxOld
        )
    }

    private fun onSpeedMaxChange(value: Int) {
        viewState = viewState.copy(
            fan1SpeedMax = value,
            fan1SpeedMaxInRange = value in (viewState.fan1SpeedMaxMin..viewState.fan1SpeedMaxMax),
            isChanged = value != viewState.fan1SpeedMaxOld || viewState.fan1SpeedMin != viewState.fan1SpeedMinOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                if (viewState.fan1SpeedMin != viewState.fan1SpeedMinOld)
                    confRepository.conf("algo-fan1SpeedMin", viewState.fan1SpeedMin)

                if (viewState.fan1SpeedMax != viewState.fan1SpeedMaxOld)
                        confRepository.conf("algo-fan1SpeedMax", viewState.fan1SpeedMax)

                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = AlgoFan1ViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}