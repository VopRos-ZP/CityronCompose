package ru.cityron.presentation.screens.algo.pi2

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.algo.pi.GetAlgoPiUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoPi2ViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getAlgoPiUseCase: GetAlgoPiUseCase,
) : BaseSharedViewModel<AlgoPi2ViewState, AlgoPi2ViewAction, AlgoPi2ViewIntent>(
    initialState = AlgoPi2ViewState()
) {

    override fun intent(viewEvent: AlgoPi2ViewIntent) {
        when (viewEvent) {
            is AlgoPi2ViewIntent.Launch -> launch()
            is AlgoPi2ViewIntent.OnSaveClick -> onSaveClick()
            is AlgoPi2ViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is AlgoPi2ViewIntent.OnPi2KofPChange -> onPi2KofPChange(viewEvent.value)
            is AlgoPi2ViewIntent.OnPi2KofIChange -> onPi2KofIChange(viewEvent.value)
            is AlgoPi2ViewIntent.OnPi2ErrChange -> onPi2ErrChange(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            val algo = getAlgoPiUseCase(2)
            viewState = viewState.copy(
                pi2KofPOld = algo.piKofP,
                pi2KofP = algo.piKofP,
                pi2KofPRange = (algo.piKofPMin..algo.piKofPMax),

                pi2KofIOld = algo.piKofI,
                pi2KofI = algo.piKofI,
                pi2KofIRange = (algo.piKofIMin..algo.piKofIMax),

                pi2ErrOld = algo.piErr,
                pi2Err = algo.piErr,
                pi2ErrRange = (algo.piErrMin..algo.piErrMax),
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onPi2KofPChange(value: Int) {
        viewState = viewState.copy(
            pi2KofP = value,
            pi2KofPInRange = value in viewState.pi2KofPRange,
        )
        updateIsChanged()
    }

    private fun onPi2KofIChange(value: Int) {
        viewState = viewState.copy(
            pi2KofI = value,
            pi2KofIInRange = value in viewState.pi2KofIRange,
        )
        updateIsChanged()
    }

    private fun onPi2ErrChange(value: Int) {
        viewState = viewState.copy(
            pi2Err = value,
            pi2ErrInRange = value in viewState.pi2ErrRange,
        )
        updateIsChanged()
    }

    private fun updateIsChanged() {
        viewState = viewState.copy(
            isChanged = viewState.pi2Err != viewState.pi2ErrOld
                    || viewState.pi2KofI != viewState.pi2KofIOld
                    || viewState.pi2KofP != viewState.pi2KofPOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                if (viewState.pi2KofP != viewState.pi2KofPOld)
                    confRepository.conf("algo-pi2KofP", viewState.pi2KofP)

                if (viewState.pi2KofI != viewState.pi2KofIOld)
                    confRepository.conf("algo-pi2KofI", viewState.pi2KofI)

                if (viewState.pi2Err != viewState.pi2ErrOld)
                    confRepository.conf("algo-pi2Err", viewState.pi2Err)

                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = AlgoPi2ViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}