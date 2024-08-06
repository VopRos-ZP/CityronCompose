package ru.cityron.presentation.screens.controller.http

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3AllUseCase
import ru.cityron.presentation.mvi.MviViewModel
import javax.inject.Inject

@HiltViewModel
class ControllerHttpViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AllUseCase: GetM3AllUseCase
) : MviViewModel<ControllerHttpViewState, ControllerHttpViewIntent>() {

    override fun intent(intent: ControllerHttpViewIntent) {
        when (intent) {
            is ControllerHttpViewIntent.Launch -> launch()
            is ControllerHttpViewIntent.OnSaveClick -> onSaveClick()
            is ControllerHttpViewIntent.OnVisibilityP1Change -> updateState {
                copy(visibilityP1 = !visibilityP1)
            }
            is ControllerHttpViewIntent.OnVisibilityP2Change -> updateState {
                copy(visibilityP2 = !visibilityP2)
            }
            is ControllerHttpViewIntent.OnFP1Change -> updateState {
                copy(fP1 = intent.value)
            }
            is ControllerHttpViewIntent.OnFP2Change -> updateState {
                copy(fP2 = intent.value)
            }
            is ControllerHttpViewIntent.OnP1Change -> updateState {
                copy(
                    p1 = intent.value,
                    isChanged = isChanged || intent.value != p1Old
                )
            }
            is ControllerHttpViewIntent.OnP2Change -> updateState {
                copy(
                    p2 = intent.value,
                    isChanged = isChanged || intent.value != p2Old
                )
            }
        }
    }

    private fun launch() {
        updateState({ copy() }, ControllerHttpViewState())
        scope.launch {
            try {
                val settings = getM3AllUseCase().settings
                updateState {
                    copy(
                        fP1 = settings.http.fP1,
                        fP2 = settings.http.fP2,

                        p1Old = settings.http.p1,
                        p1 = settings.http.p1,

                        p2Old = settings.http.p2,
                        p2 = settings.http.p2,
                    )
                }
            } catch (_: Exception) {
            }
        }
    }

    private fun onSaveClick() {
        scope.launch {
            try {
                state.value?.let {
                    confRepository.conf("http-fP1", it.fP1)
                    confRepository.conf("http-p1", it.p1)
                    confRepository.conf("http-fP2", it.fP2)
                    confRepository.conf("http-p2", it.p2)
                    updateState { copy(isChanged = false) }
                }
            } catch (_: Exception) {
                updateState { copy(isChanged = true) }
            }
        }
    }

}