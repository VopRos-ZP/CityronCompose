package ru.cityron.presentation.screens.algo.water

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.algo.water.GetAlgoWaterUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoWaterViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getAlgoWaterUseCase: GetAlgoWaterUseCase,
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
            val algo = getAlgoWaterUseCase()
            viewState = viewState.copy(
                modeZimaLetoSourceOld = algo.modeZimaLetoSource,
                modeZimaLetoSource = algo.modeZimaLetoSource,

                modeZimaLetoUserOld = algo.modeZimaLetoUser,
                modeZimaLetoUser = algo.modeZimaLetoUser,

                timeWarmUpOld = algo.timeWarmUp,
                timeWarmUp = algo.timeWarmUp,
                timeWarmUpRange = (algo.timeWarmUpMin..algo.timeWarmUpMax),

                timeDefrostOld = algo.timeDefrost,
                timeDefrost = algo.timeDefrost,
                timeDefrostRange = (algo.timeDefrostMin..algo.timeDefrostMax),
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onModeZimaLetoSourceChange(value: Int) {
        viewState = viewState.copy(modeZimaLetoSource = value)
        updateIsChanged()
    }

    private fun onModeZimaLetoUserChange(value: Int) {
        viewState = viewState.copy(modeZimaLetoUser = value)
        updateIsChanged()
    }

    private fun onTimeWarmUpChange(value: Int) {
        viewState = viewState.copy(
            timeWarmUp = value,
            timeWarmUpInRange = value in viewState.timeWarmUpRange,
        )
        updateIsChanged()
    }

    private fun onTimeDefrostChange(value: Int) {
        viewState = viewState.copy(
            timeDefrost = value,
            timeDefrostInRange = value in viewState.timeDefrostRange,
        )
        updateIsChanged()
    }

    private fun updateIsChanged() {
        viewState = viewState.copy(
            isChanged = viewState.timeDefrost != viewState.timeDefrostOld
                    || viewState.modeZimaLetoSource != viewState.modeZimaLetoSourceOld
                    || viewState.modeZimaLetoUser != viewState.modeZimaLetoUserOld
                    || viewState.timeWarmUp != viewState.timeWarmUpOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                if (viewState.modeZimaLetoSource != viewState.modeZimaLetoSourceOld)
                    confRepository.conf("algo-modeZimaLetoSource", viewState.modeZimaLetoSource)

                if (viewState.modeZimaLetoUser != viewState.modeZimaLetoUserOld)
                    confRepository.conf("algo-modeZimaLetoUser", viewState.modeZimaLetoUser)

                if (viewState.timeWarmUp != viewState.timeWarmUpOld)
                    confRepository.conf("algo-timeWarmUp", viewState.timeWarmUp)

                if (viewState.timeDefrost != viewState.timeDefrostOld)
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