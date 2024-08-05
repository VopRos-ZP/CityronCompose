package ru.cityron.presentation.screens.algo.pi2

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3SettingsUseCase
import ru.cityron.presentation.components.MviViewModel
import javax.inject.Inject

@HiltViewModel
class AlgoPi2ViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SettingsUseCase: GetM3SettingsUseCase
) : MviViewModel<AlgoPi2ViewState, AlgoPi2ViewIntent>() {

    override fun intent(intent: AlgoPi2ViewIntent) {
        when (intent) {
            is AlgoPi2ViewIntent.Launch -> launch()
            is AlgoPi2ViewIntent.OnSaveClick -> onSaveClick()
            is AlgoPi2ViewIntent.OnPi2KofPChange -> updateState {
                copy(
                    pi2KofP = intent.value,
                    isChanged = isChanged || intent.value != pi2KofPOld
                )
            }
            is AlgoPi2ViewIntent.OnPi2KofIChange -> updateState {
                copy(
                    pi2KofI = intent.value,
                    isChanged = isChanged || intent.value != pi2KofIOld
                )
            }
            is AlgoPi2ViewIntent.OnPi2ErrChange -> updateState {
                copy(
                    pi2Err = intent.value,
                    isChanged = isChanged || intent.value != pi2ErrOld
                )
            }
        }
    }

    private fun launch() {
        scope.launch {
            try {
                val settings = getM3SettingsUseCase()
                updateState(
                    { copy() }, AlgoPi2ViewState(
                        pi2KofPOld = settings.algo.pi2KofP,
                        pi2KofP = settings.algo.pi2KofP,
                        pi2KofIOld = settings.algo.pi2KofI,
                        pi2KofI = settings.algo.pi2KofI,
                        pi2ErrOld = settings.algo.pi2Err,
                        pi2Err = settings.algo.pi2Err,
                    )
                )
            } catch (_: Exception) {}
        }
    }

    private fun onSaveClick() {
        scope.launch {
            state.value?.let {
                try {
                    confRepository.conf("algo-pi2KofP", it.pi2KofP)
                    confRepository.conf("algo-pi2KofI", it.pi2KofI)
                    confRepository.conf("algo-pi2Err", it.pi2Err)
                    updateState { copy(isChanged = false) }
                } catch (_: Exception) {
                    updateState { copy(isChanged = true) }
                }
            }
        }
    }

}