package ru.cityron.presentation.screens.algo.pi1

import ru.cityron.R
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.algo.pi.GetAlgoPiUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoPi1ViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getAlgoPiUseCase: GetAlgoPiUseCase,
) : BaseSharedViewModel<AlgoPi1ViewState, AlgoPi1ViewAction, AlgoPi1ViewIntent>(
    initialState = AlgoPi1ViewState()
) {

    override fun intent(viewEvent: AlgoPi1ViewIntent) {
        when (viewEvent) {
            is AlgoPi1ViewIntent.Launch -> launch()
            is AlgoPi1ViewIntent.OnSaveClick -> onSaveClick()
            is AlgoPi1ViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is AlgoPi1ViewIntent.OnPiAutoEnChange -> onPiAutoEnChange(viewEvent.value)
            is AlgoPi1ViewIntent.OnPi1KofPChange -> onPi1KofPChange(viewEvent.value)
            is AlgoPi1ViewIntent.OnPi1KofIChange -> onPi1KofIChange(viewEvent.value)
            is AlgoPi1ViewIntent.OnPi1ErrChange -> onPi1ErrChange(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            val algo = getAlgoPiUseCase(id = 1)
            viewState = viewState.copy(
                piAutoEnOld = algo.piAutoEn!!,
                piAutoEn = algo.piAutoEn,

                piKofPOld = algo.piKofP,
                piKofP = algo.piKofP,
                piKofPRange = (algo.piKofPMin..algo.piKofPMax),

                piKofIOld = algo.piKofI,
                piKofI = algo.piKofI,
                piKofIRange = (algo.piKofIMin..algo.piKofIMax),

                piErrOld = algo.piErr,
                piErr = algo.piErr,
                piErrRange = (algo.piErrMin..algo.piErrMax),
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onPiAutoEnChange(value: Int) {
        viewState = viewState.copy(piAutoEn = value)
        updateIsChanged()
    }

    private fun onPi1KofPChange(value: Int) {
        viewState = viewState.copy(
            piKofP = value,
            piKofPInRange = value in viewState.piKofPRange,
        )
        updateIsChanged()
    }

    private fun onPi1KofIChange(value: Int) {
        viewState = viewState.copy(
            piKofI = value,
            piKofIInRange = value in viewState.piKofIRange,
        )
        updateIsChanged()
    }

    private fun onPi1ErrChange(value: Int) {
        viewState = viewState.copy(
            piErr = value,
            piErrInRange = value in viewState.piErrRange,
        )
        updateIsChanged()
    }

    private fun updateIsChanged() {
        viewState = viewState.copy(
            isChanged = viewState.piErr != viewState.piErrOld
                    || viewState.piAutoEn != viewState.piAutoEnOld
                    || viewState.piKofI != viewState.piKofIOld
                    || viewState.piKofP != viewState.piKofPOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                if (viewState.piAutoEn != viewState.piAutoEnOld)
                    confRepository.conf("algo-piAutoEn", viewState.piAutoEn)

                if (viewState.piKofP != viewState.piKofPOld)
                    confRepository.conf("algo-piKofP", viewState.piKofP)

                if (viewState.piKofI != viewState.piKofIOld)
                    confRepository.conf("algo-piKofI", viewState.piKofI)

                if (viewState.piErr != viewState.piErrOld)
                    confRepository.conf("algo-piErr", viewState.piErr)

                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = AlgoPi1ViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}