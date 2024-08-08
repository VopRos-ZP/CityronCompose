package ru.cityron.presentation.screens.addCustom

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.usecase.AddIpUseCase
import ru.cityron.domain.usecase.CheckIpAddressUseCase
import ru.cityron.domain.utils.isValidIPAddress
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class AddCustomViewModel @Inject constructor(
    private val checkIpAddressUseCase: CheckIpAddressUseCase,
    private val addIpUseCase: AddIpUseCase
) : BaseSharedViewModel<AddCustomViewState, AddCustomViewAction, AddCustomViewIntent>(
    AddCustomViewState()
) {

    override fun intent(viewEvent: AddCustomViewIntent) {
        when (viewEvent) {
            is AddCustomViewIntent.OnNextClick -> checkIpAddress()
            is AddCustomViewIntent.OnIpChange -> onIpChange(viewEvent.value)
        }
    }

    private fun onIpChange(ip: String) {
        viewState = viewState.copy(
            ip = ip,
            isCorrect = ip.isValidIPAddress()
        )
    }

    private fun checkIpAddress() {
        withViewModelScope {
            val isSuccess = checkIpAddressUseCase(viewState.ip)
            if (isSuccess) {
                addIpUseCase(viewState.ip)
            }
            viewAction = AddCustomViewAction.Snackbar(
                SnackbarResult(
                    label = if (isSuccess) R.string.success_add_controller
                    else R.string.error_add_controller,
                    isError = isSuccess.not()
                )
            )
        }
    }

}