package ru.cityron.presentation.screens.algo.electric

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.algo.electric.GetAlgoElectricUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoElectricViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getAlgoElectricUseCase: GetAlgoElectricUseCase
) : BaseSharedViewModel<AlgoElectricViewState, AlgoElectricViewAction, AlgoElectricViewIntent>(
    initialState = AlgoElectricViewState()
) {

    override fun intent(viewEvent: AlgoElectricViewIntent) {
        when (viewEvent) {
            is AlgoElectricViewIntent.Launch -> launch()
            is AlgoElectricViewIntent.OnSaveClick -> onSaveClick()
            is AlgoElectricViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is AlgoElectricViewIntent.OnHeatPwmPeriodChange -> onHeatPwmPeriodChange(viewEvent.value)
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onHeatPwmPeriodChange(value: Int) {
        viewState = viewState.copy(
            heatPwmPeriod = value,
            isChanged = value != viewState.heatPwmPeriodOld,
            isInRange = value in (viewState.min..viewState.max),
        )
    }

    private fun launch() {
        withViewModelScope {
            val algo = getAlgoElectricUseCase()
            viewState = viewState.copy(
                heatPwmPeriodOld = algo.heatPwmPeriod,
                heatPwmPeriod = algo.heatPwmPeriod,
                min = algo.heatPwmPeriodMin,
                max = algo.heatPwmPeriodMax,
            )
        }
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                if (viewState.heatPwmPeriod != viewState.heatPwmPeriodOld)
                    confRepository.conf("algo-heatPwmPeriod", viewState.heatPwmPeriod)

                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = AlgoElectricViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}