package ru.cityron.presentation.screens.algo.fan2

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3AllUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoFan2ViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AllUseCase: GetM3AllUseCase,
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
            val all = getM3AllUseCase()
            viewState = viewState.copy(
                fan2SpeedMinOld = all.settings.algo.fan2SpeedMin,
                fan2SpeedMin = all.settings.algo.fan2SpeedMin,
                fan2SpeedMinMin = all.static.settingsMin.algo.fan2SpeedMin,
                fan2SpeedMinMax = all.static.settingsMax.algo.fan2SpeedMin,

                fan2SpeedMaxOld = all.settings.algo.fan2SpeedMax,
                fan2SpeedMax = all.settings.algo.fan2SpeedMax,
                fan2SpeedMaxMin = all.static.settingsMin.algo.fan2SpeedMax,
                fan2SpeedMaxMax = all.static.settingsMax.algo.fan2SpeedMax,
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
                confRepository.conf("algo-fan2SpeedMin", viewState.fan2SpeedMin)
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