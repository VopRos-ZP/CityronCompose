package ru.cityron.presentation.screens.algo.pi2

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3AllUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AlgoPi2ViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AllUseCase: GetM3AllUseCase,
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
            val all = getM3AllUseCase()
            viewState = viewState.copy(
                pi2KofPOld = all.settings.algo.pi2KofP,
                pi2KofP = all.settings.algo.pi2KofP,
                pi2KofPRange = (all.static.settingsMin.algo.pi2KofP..all.static.settingsMax.algo.pi2KofP),

                pi2KofIOld = all.settings.algo.pi2KofI,
                pi2KofI = all.settings.algo.pi2KofI,
                pi2KofIRange = (all.static.settingsMin.algo.pi2KofI..all.static.settingsMax.algo.pi2KofI),

                pi2ErrOld = all.settings.algo.pi2Err,
                pi2Err = all.settings.algo.pi2Err,
                pi2ErrRange = (all.static.settingsMin.algo.pi2Err..all.static.settingsMax.algo.pi2Err),
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
            isChanged = value != viewState.pi2KofPOld
                    || viewState.pi2KofI != viewState.pi2KofIOld
                    || viewState.pi2Err != viewState.pi2ErrOld
        )
    }

    private fun onPi2KofIChange(value: Int) {
        viewState = viewState.copy(
            pi2KofI = value,
            pi2KofIInRange = value in viewState.pi2KofIRange,
            isChanged = value != viewState.pi2KofIOld
                    || viewState.pi2KofP != viewState.pi2KofPOld
                    || viewState.pi2Err != viewState.pi2ErrOld
        )
    }

    private fun onPi2ErrChange(value: Int) {
        viewState = viewState.copy(
            pi2Err = value,
            pi2ErrInRange = value in viewState.pi2ErrRange,
            isChanged = value != viewState.pi2ErrOld
                    || viewState.pi2KofI != viewState.pi2KofIOld
                    || viewState.pi2KofP != viewState.pi2KofPOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                confRepository.conf("algo-pi2KofP", viewState.pi2KofP)
                confRepository.conf("algo-pi2KofI", viewState.pi2KofI)
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