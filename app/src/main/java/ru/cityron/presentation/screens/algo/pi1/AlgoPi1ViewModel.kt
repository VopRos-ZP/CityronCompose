package ru.cityron.presentation.screens.algo.pi1

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3SettingsUseCase
import ru.cityron.presentation.mvi.MviViewModel
import javax.inject.Inject

@HiltViewModel
class AlgoPi1ViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SettingsUseCase: GetM3SettingsUseCase
) : MviViewModel<AlgoPi1ViewState, AlgoPi1ViewIntent>() {

    override fun intent(intent: AlgoPi1ViewIntent) {
        when (intent) {
            is AlgoPi1ViewIntent.Launch -> launch()
            is AlgoPi1ViewIntent.OnSaveClick -> onSaveClick()
            is AlgoPi1ViewIntent.OnPiAutoEnChange -> updateState {
                copy(
                    piAutoEn = intent.value,
                    isChanged = isChanged || intent.value != piAutoEnOld
                )
            }
            is AlgoPi1ViewIntent.OnPi1KofPChange -> updateState {
                copy(
                    piKofP = intent.value,
                    isChanged = isChanged || intent.value != piKofPOld
                )
            }
            is AlgoPi1ViewIntent.OnPi1KofIChange -> updateState {
                copy(
                    piKofI = intent.value,
                    isChanged = isChanged || intent.value != piKofIOld
                )
            }
            is AlgoPi1ViewIntent.OnPi1ErrChange -> updateState {
                copy(
                    piErr = intent.value,
                    isChanged = isChanged || intent.value != piErrOld
                )
            }
        }
    }

    private fun launch() {
        scope.launch {
            try {
                val settings = getM3SettingsUseCase()
                updateState(
                    { copy() }, AlgoPi1ViewState(
                        piAutoEnOld = settings.algo.piAutoEn,
                        piAutoEn = settings.algo.piAutoEn,
                        piKofPOld = settings.algo.piKofP,
                        piKofP = settings.algo.piKofP,
                        piKofIOld = settings.algo.piKofI,
                        piKofI = settings.algo.piKofI,
                        piErrOld = settings.algo.piErr,
                        piErr = settings.algo.piErr,
                    )
                )
            } catch (_: Exception) {}
        }
    }

    private fun onSaveClick() {
        scope.launch {
            state.value?.let {
                try {
                    confRepository.conf("algo-piAutoEn", it.piAutoEn)
                    confRepository.conf("algo-piKofP", it.piKofP)
                    confRepository.conf("algo-piKofI", it.piKofI)
                    confRepository.conf("algo-piErr", it.piErr)
                    updateState { copy(isChanged = false) }
                } catch (_: Exception) {
                    updateState { copy(isChanged = true) }
                }
            }
        }
    }

}