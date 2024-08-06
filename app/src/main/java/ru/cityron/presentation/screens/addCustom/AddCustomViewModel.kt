package ru.cityron.presentation.screens.addCustom

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.R
import ru.cityron.domain.usecase.AddIpUseCase
import ru.cityron.domain.usecase.CheckIpAddressUseCase
import ru.cityron.presentation.mvi.MviViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AddCustomViewModel @Inject constructor(
    private val checkIpAddressUseCase: CheckIpAddressUseCase,
    private val addIpUseCase: AddIpUseCase
) : MviViewModel<AddCustomViewState, AddCustomViewIntent>() {

    override fun intent(intent: AddCustomViewIntent) {
        when (intent) {
            is AddCustomViewIntent.Launch -> updateState({ copy() }, AddCustomViewState())
            is AddCustomViewIntent.OnNextClick -> checkIpAddress()
            is AddCustomViewIntent.OnIpChange -> updateState {
                copy(
                    ip = intent.value,
                    isCorrect = isValidIPAddress(intent.value)
                )
            }

            is AddCustomViewIntent.OnSnackbarResultChange -> updateState {
                copy(result = intent.value)
            }
        }
    }

    private fun checkIpAddress() {
        scope.launch {
            val ip = state.value!!.ip
            val isSuccess = checkIpAddressUseCase(ip)
            if (isSuccess) {
                addIpUseCase(ip)
            }
            updateState {
                copy(
                    result = SnackbarResult(
                        label = if (isSuccess) R.string.success_add_controller
                        else R.string.error_add_controller,
                        isError = isSuccess.not()
                    ),
                )
            }
        }
    }

    private fun isValidIPAddress(ip: String): Boolean {
        val ipRegex =
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
        return ip.matches(ipRegex.toRegex())
    }

}