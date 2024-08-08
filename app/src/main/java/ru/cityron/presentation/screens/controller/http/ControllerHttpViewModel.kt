package ru.cityron.presentation.screens.controller.http

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3AllUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class ControllerHttpViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AllUseCase: GetM3AllUseCase
) : BaseSharedViewModel<ControllerHttpViewState, ControllerHttpViewAction, ControllerHttpViewIntent>(
    initialState = ControllerHttpViewState()
) {

    override fun intent(viewEvent: ControllerHttpViewIntent) {
        when (viewEvent) {
            is ControllerHttpViewIntent.Launch -> launch()
            is ControllerHttpViewIntent.OnSaveClick -> onSaveClick()
            is ControllerHttpViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is ControllerHttpViewIntent.OnVisibilityP1Change -> onVisibilityP1Change()
            is ControllerHttpViewIntent.OnVisibilityP2Change -> onVisibilityP2Change()
            is ControllerHttpViewIntent.OnFP1Change -> onFP1Change(viewEvent.value)
            is ControllerHttpViewIntent.OnFP2Change -> onFP2Change(viewEvent.value)
            is ControllerHttpViewIntent.OnP1Change -> onP1Change(viewEvent.value)
            is ControllerHttpViewIntent.OnP2Change -> onP2Change(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            val settings = getM3AllUseCase().settings
            viewState = viewState.copy(
                fP1 = settings.http.fP1,
                fP2 = settings.http.fP2,

                p1Old = settings.http.p1,
                p1 = settings.http.p1,

                p2Old = settings.http.p2,
                p2 = settings.http.p2,
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onVisibilityP1Change() {
        viewState = viewState.copy(visibilityP1 = !viewState.visibilityP1)
    }

    private fun onVisibilityP2Change() {
        viewState = viewState.copy(visibilityP2 = !viewState.visibilityP2)
    }

    private fun onFP1Change(value: Int) {
        viewState = viewState.copy(fP1 = value)
    }

    private fun onFP2Change(value: Int) {
        viewState = viewState.copy(fP2 = value)
    }

    private fun onP1Change(value: String) {
        viewState = viewState.copy(
            p1 = value,
            isChanged = value != viewState.p1Old || viewState.p2 != viewState.p2Old
        )
    }

    private fun onP2Change(value: String) {
        viewState = viewState.copy(
            p2 = value,
            isChanged = value != viewState.p2Old || viewState.p1 != viewState.p1Old
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                confRepository.conf("http-fP1", viewState.fP1)
                confRepository.conf("http-fP2", viewState.fP2)

                if (viewState.p1 != viewState.p1Old)
                    confRepository.conf("http-p1", viewState.p1)

                if (viewState.p2 != viewState.p2Old)
                    confRepository.conf("http-p2", viewState.p2)

                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = ControllerHttpViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}