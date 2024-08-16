package ru.cityron.presentation.screens.m3tabs

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.model.Controller
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.usecase.all.state.GetM3StateUseCase
import ru.cityron.domain.usecase.controller.GetControllersUseCase
import ru.cityron.domain.usecase.events.DeleteEventsUseCase
import ru.cityron.domain.usecase.events.GetFiltersUseCase
import ru.cityron.domain.usecase.events.UpsertEventsUseCase
import ru.cityron.domain.utils.toInt
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.screens.m3temp.M3TempViewIntent
import ru.cityron.presentation.screens.m3temp.M3TempViewState
import javax.inject.Inject

@HiltViewModel
class M3ViewModel @Inject constructor(
    private val currentRepository: CurrentRepository,
    private val getControllersUseCase: GetControllersUseCase,
    private val getM3StateUseCase: GetM3StateUseCase,
    private val confRepository: ConfRepository,
    private val upsertEventsUseCase: UpsertEventsUseCase,
    private val getFiltersUseCase: GetFiltersUseCase,
    private val deleteEventsUseCase: DeleteEventsUseCase,
) : BaseSharedViewModel<M3TempViewState, Any, M3TempViewIntent>(
    initialState = M3TempViewState()
) {

    private val _controller = MutableStateFlow(Controller())
    val controller = currentRepository.current

    override fun intent(viewEvent: M3TempViewIntent) {
        when (viewEvent) {
            is M3TempViewIntent.Launch -> launch()
            is M3TempViewIntent.OnTempChange -> setTemp(viewEvent.value)
            is M3TempViewIntent.OnFanChange -> setFan(viewEvent.value)
            is M3TempViewIntent.OnIsShowOnOffDialogChange -> viewState = viewState.copy(isShowOnOffDialog = viewEvent.value)
            is M3TempViewIntent.OnConfirmOnOffClick -> onOffController()
        }
    }

    private fun launch() {
        withViewModelScope {
//            launch {
//                currentRepository.current.value?.let { controller ->
//                    try {
//                        getControllersUseCase.listenOne(controller.id).collect {
//                            _controller.value = it
//                        }
//                    }  catch (_: Exception) {}
//                }
//            }
            launch {
                controller.collect {
                    getFiltersUseCase.flow.collect {
                        deleteEventsUseCase()
                        upsertEventsUseCase(it)
                    }
                }
            }
            launch {
                getM3StateUseCase.flow.collect {
                    viewState = viewState.copy(
                        temp = it.set.temp,
                        tempPv = it.algo.tempPv,
                        fan = it.set.fan,
                        isPowerOn = it.set.power == 1,
                        isShowAlarms = it.alarms != 0
                    )
                }
            }
        }
    }

    private fun setTemp(temp: Int) {
        withViewModelScope {
            confRepository.conf("set-temp", temp)
        }
    }

    private fun setFan(fan: Int) {
        withViewModelScope {
            confRepository.conf("set-fan", fan)
        }
    }

    private fun onOffController() {
        withViewModelScope {
            confRepository.conf("set-power", (!viewState.isPowerOn).toInt())
        }
    }

}