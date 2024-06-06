package ru.cityron.presentation.screens.addCustom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.usecase.AddIpUseCase
import ru.cityron.domain.usecase.CheckIpAddressUseCase
import javax.inject.Inject

@HiltViewModel
class AddCustomViewModel @Inject constructor(
    private val checkIpAddressUseCase: CheckIpAddressUseCase,
    private val addIpUseCase: AddIpUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(Custom.State())
    val state = _state.asStateFlow()

    fun closeSnackbar() {
        _state.value = state.value.copy(isShowSnackbar = false)
    }

    fun ipChanged(ip: String) {
        _state.value = state.value.copy(
            ip = ip,
            isCorrect = isValidIPAddress(ip)
        )
    }

    fun checkIpAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            val isSuccess = checkIpAddressUseCase(state.value.ip)
            if (isSuccess) {
                addIpUseCase(state.value.ip)
            }
            _state.value = state.value.copy(
                isErrorChecked = isSuccess.not(),
                isShowSnackbar = isSuccess.not()
            )
        }
    }

    private fun isValidIPAddress(ip: String): Boolean {
        val ipRegex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
        return ip.matches(ipRegex.toRegex())
    }

}