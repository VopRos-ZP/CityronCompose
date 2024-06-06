package ru.cityron.presentation.screens.changeName

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.CurrentRepository
import javax.inject.Inject

@HiltViewModel
class ChangeNameViewModel @Inject constructor(
    private val currentRepository: CurrentRepository,
    private val confRepository: ConfRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChangeName.State())
    val state = _state.asStateFlow()

    fun fetchOldName() {
        val oldName = currentRepository.current?.first?.name
            ?.split(" (")?.get(1)
            ?.replace(")", "") ?: ""
        _state.value = when (oldName.all { it.isUpperCase() }) {
            true -> state.value.copy(oldName = "")
            else -> state.value.copy(oldName = oldName)
        }
    }

    fun closeSnackbar() {
        _state.value = state.value.copy(isShowSnackbar = false)
    }

    fun onNameChange(name: String) {
        _state.value = state.value.copy(name = name)
    }

    fun onSaveClick() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                confRepository.conf("name", state.value.name)
                //And save new name locally
                _state.value = state.value.copy(
                    isErrorChecked = false,
                    isShowSnackbar = false
                )
            } catch (_: Exception) {
                _state.value = state.value.copy(
                    isErrorChecked = true,
                    isShowSnackbar = true
                )
            }
        }
    }

}