package ru.cityron.presentation.screens.changeName

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class ChangeNameViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val currentRepository: CurrentRepository,
) : BaseSharedViewModel<ChangeNameViewState, ChangeNameViewAction, ChangeNameViewIntent>(
    initialState = ChangeNameViewState()
) {

    override fun intent(viewEvent: ChangeNameViewIntent) {
        when (viewEvent) {
            is ChangeNameViewIntent.Launch -> launch()
            is ChangeNameViewIntent.OnSaveClick -> onSaveClick()
            is ChangeNameViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is ChangeNameViewIntent.OnNameChange -> onNameChange(viewEvent.value)
        }
    }

    private fun launch() {
        val oldName = currentRepository.current?.first?.name
            ?.split(" (")?.get(1)
            ?.replace(")", "") ?: ""
        viewState = when (oldName == currentRepository.current?.first?.idUsr) {
            true -> viewState.copy(oldName = "", name = "")
            else -> viewState.copy(oldName = oldName, name = oldName)
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onNameChange(value: String) {
        viewState = viewState.copy(
            name = value,
            isChanged = value != viewState.oldName
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                confRepository.conf("others-loc", viewState.name.plus("\n"))// Перенос строки обязателен!!!
                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = ChangeNameViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}