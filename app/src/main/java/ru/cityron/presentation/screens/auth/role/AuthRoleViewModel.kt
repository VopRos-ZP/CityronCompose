package ru.cityron.presentation.screens.auth.role

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class AuthRoleViewModel @Inject constructor(

) : BaseSharedViewModel<AuthRoleViewState, Any, AuthRoleViewIntent>(
    initialState = AuthRoleViewState()
) {

    override fun intent(viewEvent: AuthRoleViewIntent) {
        when (viewEvent) {
            is AuthRoleViewIntent.OnPasswordChange -> onPasswordChange(viewEvent.value)
        }
    }

    private fun onPasswordChange(value: String) {
        viewState = viewState.copy(password = value)
    }

}