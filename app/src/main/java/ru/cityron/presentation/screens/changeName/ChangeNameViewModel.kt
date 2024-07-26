package ru.cityron.presentation.screens.changeName

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.ControllerRepository
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.presentation.components.MviViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeNameViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val currentRepository: CurrentRepository,
    private val controllerRepository: ControllerRepository,
) : MviViewModel<ChangeNameViewState, ChangeNameViewIntent>() {

    override fun intent(intent: ChangeNameViewIntent) {
        when (intent) {
            is ChangeNameViewIntent.Launch -> launch()
            is ChangeNameViewIntent.OnSaveClick -> onSaveClick()
            is ChangeNameViewIntent.OnNameChange -> updateState {
                copy(
                    name = intent.value.trim(),
                    isChanged = intent.value != oldName
                )
            }

            is ChangeNameViewIntent.OnIsErrorCheckedChange -> updateState {
                copy(isErrorChecked = intent.value)
            }

            is ChangeNameViewIntent.OnIsShowSnakbarChange -> updateState {
                copy(isShowSnackbar = intent.value)
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
                    val controller = currentRepository.current!!.first
                    val devName = controller.name.split(" ")[0]
                    controllerRepository.upsert(controller.copy(name = "$devName (${it.name})"))
                    updateState {
                        copy(
                            isErrorChecked = false,
                            isShowSnackbar = false
                        )
                    }
                } catch (_: Exception) {
                    updateState {
                        copy(
                            isErrorChecked = true,
                            isShowSnackbar = true
                        )
                    }
                }
            }
        }
    }

}