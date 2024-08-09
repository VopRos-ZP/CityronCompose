package ru.cityron.presentation.screens.addController

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class AddControllerViewModel @Inject constructor(

) : BaseSharedViewModel<AddControllerViewState, Any, AddControllerViewIntent>(
    initialState = AddControllerViewState()
) {

    override fun intent(viewEvent: AddControllerViewIntent) {
        when (viewEvent) {
            is AddControllerViewIntent.OnCodeChange -> onCodeChange(viewEvent.value)
        }
    }

    private fun onCodeChange(value: String) {
        viewState = viewState.copy(code = value)
    }

}