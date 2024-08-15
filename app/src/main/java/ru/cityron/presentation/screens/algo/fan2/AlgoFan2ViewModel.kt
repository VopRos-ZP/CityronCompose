package ru.cityron.presentation.screens.algo.fan2

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.algo.fan.GetAlgoFanUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoFan2ViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getAlgoFanUseCase: GetAlgoFanUseCase,
) : BaseSharedViewModel<AlgoFan2ViewState, AlgoFan2ViewAction, AlgoFan2ViewIntent>(
    initialState = AlgoFan2ViewState()
) {

    override fun intent(viewEvent: AlgoFan2ViewIntent) {
        when (viewEvent) {
            is AlgoFan2ViewIntent.Launch -> launch()
            is AlgoFan2ViewIntent.OnSaveClick -> onSaveClick()
            is AlgoFan2ViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is AlgoFan2ViewIntent.OnSpeedMinChange -> onSpeedMinChange(viewEvent.value)
            is AlgoFan2ViewIntent.OnSpeedMaxChange -> onSpeedMaxChange(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            val algo = getAlgoFanUseCase(2)
            viewState = viewState.copy(
                fan2SpeedMinOld = algo.fanSpeedMin,
                fan2SpeedMin = algo.fanSpeedMin,
                fan2SpeedMinMin = algo.fanSpeedMinMin,
                fan2SpeedMinMax = algo.fanSpeedMinMax,

                fan2SpeedMaxOld = algo.fanSpeedMax,
                fan2SpeedMax = algo.fanSpeedMax,
                fan2SpeedMaxMin = algo.fanSpeedMaxMin,
                fan2SpeedMaxMax = algo.fanSpeedMaxMax,
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onSpeedMinChange(value: Int) {
        viewState = viewState.copy(
            fan2SpeedMin = value,
            fan2SpeedMinInRange = value in (viewState.fan2SpeedMinMin..viewState.fan2SpeedMinMax),
            isChanged = value != viewState.fan2SpeedMinOld || viewState.fan2SpeedMax != viewState.fan2SpeedMaxOld
        )
    }

    private fun onSpeedMaxChange(value: Int) {
        viewState = viewState.copy(
            fan2SpeedMax = value,
            fan2SpeedMaxInRange = value in (viewState.fan2SpeedMaxMin..viewState.fan2SpeedMaxMax),
            isChanged = value != viewState.fan2SpeedMaxOld || viewState.fan2SpeedMin != viewState.fan2SpeedMinOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                if (viewState.fan2SpeedMin != viewState.fan2SpeedMinOld)
                    confRepository.conf("algo-fan2SpeedMin", viewState.fan2SpeedMin)

                if (viewState.fan2SpeedMax != viewState.fan2SpeedMaxOld)
                    confRepository.conf("algo-fan2SpeedMax", viewState.fan2SpeedMax)

                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = AlgoFan2ViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}