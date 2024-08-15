package ru.cityron.presentation.screens.root

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.cityron.domain.model.Controller
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.usecase.controller.GetControllersUseCase
import ru.cityron.domain.usecase.def.GetDefaultUseCase
import ru.cityron.domain.usecase.def.UpsertDefaultUseCase
import ru.cityron.domain.usecase.events.DeleteEventsUseCase
import ru.cityron.domain.usecase.events.GetFiltersUseCase
import ru.cityron.domain.usecase.events.UpsertEventsUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val currentRepository: CurrentRepository,
    private val getControllersUseCase: GetControllersUseCase,
    private val getDefaultUseCase: GetDefaultUseCase,
    private val upsertDefaultUseCase: UpsertDefaultUseCase
) : BaseSharedViewModel<RootViewState, Any, RootViewIntent>(
    initialState = RootViewState()
) {

    init {
        withViewModelScope {
            launch {
                getDefaultUseCase {
                    upsertDefaultUseCase()
                    getDefaultUseCase.setIsFirstLaunch(false)
                }
            }
        }
    }

    override fun intent(viewEvent: RootViewIntent) {
        when (viewEvent) {
            is RootViewIntent.Launch -> launch()
            is RootViewIntent.OnSelectController -> onSelectController(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            while (true) {
                viewState  = viewState.copy(controllers = getControllersUseCase())
                delay(1000)
            }
        }
    }

    private fun onSelectController(pair: Controller) {
        withViewModelScope {
            currentRepository.setCurrentController(pair)
        }
    }

}