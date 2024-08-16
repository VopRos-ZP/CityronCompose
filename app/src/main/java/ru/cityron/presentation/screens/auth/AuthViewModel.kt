package ru.cityron.presentation.screens.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.repository.BindCurrentRepository
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val currentRepository: CurrentRepository,
    private val bindCurrentRepository: BindCurrentRepository,
) : BaseSharedViewModel<AuthViewState, AuthViewAction, AuthViewIntent>(
    initialState = AuthViewState()
) {

    override fun intent(viewEvent: AuthViewIntent) {
        when (viewEvent) {
            is AuthViewIntent.Launch -> launch(viewEvent.items)
            is AuthViewIntent.OnDispose -> onDispose()
            is AuthViewIntent.OnItemClick -> onItemClick(viewEvent.value)
        }
    }

    private fun launch(items: List<String>) {
        withViewModelScope {
            viewState = viewState.copy(items = items)
        }
    }

    private fun onDispose() {
        viewAction = null
    }

    private fun onItemClick(value: String) {
        withViewModelScope {
            viewAction = when (bindCurrentRepository.controller == null) {
                true -> {
                    bindCurrentRepository.controller = currentRepository.current.value
                    AuthViewAction.OnNavigate(Screen.AuthRole(value))
                }
                else -> AuthViewAction.OnNavigate(Screen.AddController(value))
            }
        }
    }

}