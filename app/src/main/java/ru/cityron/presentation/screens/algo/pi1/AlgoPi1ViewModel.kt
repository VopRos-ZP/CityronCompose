package ru.cityron.presentation.screens.algo.pi1

import ru.cityron.R
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3AllUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoPi1ViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AllUseCase: GetM3AllUseCase,
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
            val all = getM3AllUseCase()
            viewState = viewState.copy(
                piAutoEnOld = all.settings.algo.piAutoEn,
                piAutoEn = all.settings.algo.piAutoEn,

                piKofPOld = all.settings.algo.piKofP,
                piKofP = all.settings.algo.piKofP,
                piKofPRange = (all.static.settingsMin.algo.piKofP..all.static.settingsMax.algo.piKofP),

                piKofIOld = all.settings.algo.piKofI,
                piKofI = all.settings.algo.piKofI,
                piKofIRange = (all.static.settingsMin.algo.piKofI..all.static.settingsMax.algo.piKofI),

                piErrOld = all.settings.algo.piErr,
                piErr = all.settings.algo.piErr,
                piErrRange = (all.static.settingsMin.algo.piErr..all.static.settingsMax.algo.piErr),
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onPiAutoEnChange(value: Int) {
        viewState = viewState.copy(
            piAutoEn = value,
            isChanged = value != viewState.piAutoEnOld
                    || viewState.piKofP != viewState.piKofPOld
                    || viewState.piKofI != viewState.piKofIOld
                    || viewState.piErr != viewState.piErrOld
        )
    }

    private fun onPi1KofPChange(value: Int) {
        viewState = viewState.copy(
            piKofP = value,
            piKofPInRange = value in viewState.piKofPRange,
            isChanged = value != viewState.piKofPOld
                    || viewState.piAutoEn != viewState.piAutoEnOld
                    || viewState.piKofI != viewState.piKofIOld
                    || viewState.piErr != viewState.piErrOld
        )
    }

    private fun onPi1KofIChange(value: Int) {
        viewState = viewState.copy(
            piKofI = value,
            piKofIInRange = value in viewState.piKofIRange,
            isChanged = value != viewState.piKofIOld
                    || viewState.piAutoEn != viewState.piAutoEnOld
                    || viewState.piKofP != viewState.piKofPOld
                    || viewState.piErr != viewState.piErrOld
        )
    }

    private fun onPi1ErrChange(value: Int) {
        viewState = viewState.copy(
            piErr = value,
            piErrInRange = value in viewState.piErrRange,
            isChanged = value != viewState.piErrOld
                    || viewState.piAutoEn != viewState.piAutoEnOld
                    || viewState.piKofI != viewState.piKofIOld
                    || viewState.piKofP != viewState.piKofPOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                confRepository.conf("algo-piAutoEn", viewState.piAutoEn)
                confRepository.conf("algo-piKofP", viewState.piKofP)
                confRepository.conf("algo-piKofI", viewState.piKofI)
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