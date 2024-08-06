package ru.cityron.presentation.screens.changeName

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.presentation.mvi.MviViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class ChangeNameViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val currentRepository: CurrentRepository,
) : MviViewModel<ChangeNameViewState, ChangeNameViewIntent>() {

    override fun intent(intent: ChangeNameViewIntent) {
        when (intent) {
            is ChangeNameViewIntent.Launch -> launch()
            is ChangeNameViewIntent.OnSaveClick -> onSaveClick()
            is ChangeNameViewIntent.OnNameChange -> updateState {
                copy(
                    name = intent.value,
                    isChanged = intent.value != oldName
                )
            }
            is ChangeNameViewIntent.OnSnakbarResultChange -> updateState {
                copy(result = intent.value)
            }
        }
    }

    private fun launch() {
        updateState({ copy() }, ChangeNameViewState())

        val oldName = currentRepository.current?.first?.name
            ?.split(" (")?.get(1)
            ?.replace(")", "") ?: ""

        updateState {
            when (oldName == currentRepository.current?.first?.idUsr) {
                true -> copy(oldName = "", name = "")
                else -> copy(oldName = oldName, name = oldName)
            }
        }
    }

    private fun onSaveClick() {
        scope.launch {
            state.value?.let {
                try {
                    confRepository.conf("others-loc", it.name.plus("\n"))// Перенос строки обязателен!!!
                    updateState {
                        copy(
                            isChanged = false,
                            result = SnackbarResult(R.string.success_save_settings, false)
                        )
                    }
                } catch (_: Exception) {
                    updateState {
                        copy(
                            isChanged = true,
                            result = SnackbarResult(R.string.error_save_settings, true)
                        )
                    }
                }
            }
        }
    }

}