package ru.cityron.presentation.screens.algo.water

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3AllUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoWaterViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AllUseCase: GetM3AllUseCase,
) : BaseSharedViewModel<AlgoWaterViewState, AlgoWaterViewAction, AlgoWaterViewIntent>(
    initialState = AlgoWaterViewState()
) {

    override fun intent(viewEvent: AlgoWaterViewIntent) {
        when (viewEvent) {
            is AlgoWaterViewIntent.Launch -> launch()
            is AlgoWaterViewIntent.OnSaveClick -> onSaveClick()
            is AlgoWaterViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is AlgoWaterViewIntent.OnModeZimaLetoSourceChange -> onModeZimaLetoSourceChange(viewEvent.value)
            is AlgoWaterViewIntent.OnModeZimaLetoUserChange -> onModeZimaLetoUserChange(viewEvent.value)
            is AlgoWaterViewIntent.OnTimeWarmUpChange -> onTimeWarmUpChange(viewEvent.value)
            is AlgoWaterViewIntent.OnTimeDefrostChange -> onTimeDefrostChange(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            val all = getM3AllUseCase()
            viewState = viewState.copy(
                modeZimaLetoSourceOld = all.settings.algo.modeZimaLetoSource,
                modeZimaLetoSource = all.settings.algo.modeZimaLetoSource,

                modeZimaLetoUserOld = all.settings.algo.modeZimaLetoUser,
                modeZimaLetoUser = all.settings.algo.modeZimaLetoUser,

                timeWarmUpOld = all.settings.algo.timeWarmUp,
                timeWarmUp = all.settings.algo.timeWarmUp,

                timeDefrostOld = all.settings.algo.timeDefrost,
                timeDefrost = all.settings.algo.timeDefrost,
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onModeZimaLetoSourceChange(value: Int) {
        viewState = viewState.copy(
            modeZimaLetoSource = value,
            isChanged = value != viewState.modeZimaLetoSourceOld
                    || viewState.modeZimaLetoUser != viewState.modeZimaLetoUserOld
                    || viewState.timeWarmUp != viewState.timeWarmUpOld
                    || viewState.timeDefrost != viewState.timeDefrostOld
        )
    }

    private fun onModeZimaLetoUserChange(value: Int) {
        viewState = viewState.copy(
            modeZimaLetoUser = value,
            isChanged = value != viewState.modeZimaLetoUserOld
                    || viewState.modeZimaLetoSource != viewState.modeZimaLetoSourceOld
                    || viewState.timeWarmUp != viewState.timeWarmUpOld
                    || viewState.timeDefrost != viewState.timeDefrostOld
        )
    }

    private fun onTimeWarmUpChange(value: Int) {
        viewState = viewState.copy(
            timeWarmUp = value,
            timeWarmUpInRange = value in viewState.timeWarmUpRange,
            isChanged = value != viewState.timeWarmUpOld
                    || viewState.modeZimaLetoSource != viewState.modeZimaLetoSourceOld
                    || viewState.modeZimaLetoUser != viewState.modeZimaLetoUserOld
                    || viewState.timeDefrost != viewState.timeDefrostOld
        )
    }

    private fun onTimeDefrostChange(value: Int) {
        viewState = viewState.copy(
            timeDefrost = value,
            timeDefrostInRange = value in viewState.timeDefrostRange,
            isChanged = value != viewState.timeDefrostOld
                    || viewState.modeZimaLetoSource != viewState.modeZimaLetoSourceOld
                    || viewState.modeZimaLetoUser != viewState.modeZimaLetoUserOld
                    || viewState.timeWarmUp != viewState.timeWarmUpOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                confRepository.conf("algo-modeZimaLetoSource", viewState.modeZimaLetoSource)
                confRepository.conf("algo-modeZimaLetoUser", viewState.modeZimaLetoUser)
                confRepository.conf("algo-timeWarmUp", viewState.timeWarmUp)
                confRepository.conf("algo-timeDefrost", viewState.timeDefrost)
                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = AlgoWaterViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}