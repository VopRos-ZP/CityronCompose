package ru.cityron.presentation.screens.auth.role

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.repository.BindCurrentRepository
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.usecase.access.GetAccessUseCase
import ru.cityron.domain.usecase.bind.BindAuthUseCase
import ru.cityron.domain.usecase.controller.UpsertControllerUseCase
import ru.cityron.domain.usecase.device.GetDeviceUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class AuthRoleViewModel @Inject constructor(
    private val getAccessUseCase: GetAccessUseCase,
    private val bindAuthUseCase: BindAuthUseCase,
    private val currentRepository: CurrentRepository,
    private val bindCurrentRepository: BindCurrentRepository,
    private val getDeviceUseCase: GetDeviceUseCase,
    private val upsertControllerUseCase: UpsertControllerUseCase,
) : BaseSharedViewModel<AuthRoleViewState, AuthRoleViewAction, AuthRoleViewIntent>(
    initialState = AuthRoleViewState()
) {

    override fun intent(viewEvent: AuthRoleViewIntent) {
        when (viewEvent) {
            is AuthRoleViewIntent.Launch -> launch(viewEvent.accessLevel)
            is AuthRoleViewIntent.OnPasswordChangeFinish -> onPasswordChangeFinish(viewEvent.accessLevel)
            is AuthRoleViewIntent.OnPasswordChange -> onPasswordChange(viewEvent.value)
        }
    }

    private fun launch(accessLevel: String) {
        withViewModelScope {
            val (isPassEn, length) = getAccessUseCase(accessLevel)
            viewState = viewState.copy(length = length)
            if (!isPassEn) {
                if (bindAuthUseCase.auth(
                        num = bindCurrentRepository.controller.num,
                        appUuid = getDeviceUseCase().deviceId
                    )
                ) {
                    onSuccessAuth()
                }
            } else {
                viewState = viewState.copy(isLoading = false)
            }
        }
    }

    private fun onPasswordChange(value: String) {
        viewState = viewState.copy(password = value)
    }

    private fun onPasswordChangeFinish(accessLevel: String) {
        withViewModelScope {
            if (bindAuthUseCase.auth(pass = viewState.password, level = accessLevel.first())) {
                onSuccessAuth()
            }
        }
    }

    private fun onSuccessAuth() {
        withViewModelScope {
            val controller = bindCurrentRepository.controller
            upsertControllerUseCase(controller)
            currentRepository.setCurrentController(controller)
        }
    }

}