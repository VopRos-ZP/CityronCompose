package ru.cityron.presentation.screens.addController

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.repository.BindCurrentRepository
import ru.cityron.domain.usecase.bind.BindAddUseCase
import ru.cityron.domain.usecase.bind.BindCancelUseCase
import ru.cityron.domain.usecase.bind.BindConfirmUseCase
import ru.cityron.domain.utils.toControllerNum
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class AddControllerViewModel @Inject constructor(
    private val bindAddUseCase: BindAddUseCase,
    private val bindCancelUseCase: BindCancelUseCase,
    private val bindConfirmUseCase: BindConfirmUseCase,
    private val bindCurrentRepository: BindCurrentRepository,
) : BaseSharedViewModel<AddControllerViewState, AddControllerViewAction, AddControllerViewIntent>(
    initialState = AddControllerViewState()
) {

    override fun intent(viewEvent: AddControllerViewIntent) {
        when (viewEvent) {
            is AddControllerViewIntent.Launch -> launch(viewEvent.accessLevel)
            is AddControllerViewIntent.OnDispose -> onDispose()
            is AddControllerViewIntent.OnCodeChange -> onCodeChange(viewEvent.value)
            is AddControllerViewIntent.OnCodeChangeFinish -> onCodeChangeFinish()
        }
    }

    private fun launch(accessLevel: String) {
        withViewModelScope {
            bindAddUseCase(accessLevel)
        }
    }

    private fun onDispose() {
        withViewModelScope { bindCancelUseCase() }
        viewAction = null
    }

    private fun onCodeChange(value: String) {
        viewState = viewState.copy(code = value)
    }

    private fun onCodeChangeFinish() {
        withViewModelScope {
            try {
                val response = bindConfirmUseCase(viewState.code)
                bindCurrentRepository.controller = bindCurrentRepository.controller?.copy(num = response.num.toControllerNum())
                viewAction = AddControllerViewAction.Success
            } catch (_: Exception) {
                viewAction = null
            }
        }
    }

}