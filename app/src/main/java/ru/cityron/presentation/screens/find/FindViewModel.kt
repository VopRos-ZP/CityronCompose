package ru.cityron.presentation.screens.find

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.model.Controller
import ru.cityron.domain.repository.BindCurrentRepository
import ru.cityron.domain.usecase.GetInfoListUseCase
import ru.cityron.domain.utils.toController
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class FindViewModel @Inject constructor(
    private val getInfoListUseCase: GetInfoListUseCase,
    private val bindCurrentRepository: BindCurrentRepository,
) : BaseSharedViewModel<FindViewState, Any, FindViewIntent>(
    initialState = FindViewState()
) {

    init {
        withViewModelScope {
            getInfoListUseCase.infoList.collect { map ->
                viewState = viewState.copy(controllers = map.mapKeys { it.key.toController() })
            }
        }
    }

    override fun intent(viewEvent: FindViewIntent) {
        when (viewEvent) {
            is FindViewIntent.OnAddClick -> onAddClick(viewEvent.value)
        }
    }

    private fun onAddClick(controller: Controller) {
        bindCurrentRepository.controller = controller
    }

}