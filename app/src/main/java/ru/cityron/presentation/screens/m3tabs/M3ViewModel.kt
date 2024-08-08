package ru.cityron.presentation.screens.m3tabs

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.repository.M3Repository
import ru.cityron.domain.utils.toInt
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.screens.m3temp.M3TempViewIntent
import ru.cityron.presentation.screens.m3temp.M3TempViewState
import javax.inject.Inject

@HiltViewModel
class M3ViewModel @Inject constructor(
    private val currentRepository: CurrentRepository,
    private val m3Repository: M3Repository,
    private val confRepository: ConfRepository,
) : BaseSharedViewModel<M3TempViewState, Any, M3TempViewIntent>(
    initialState = M3TempViewState()
) {

    private val _controller = MutableStateFlow<Pair<Controller, DataSource>?>(null)
    val controller = _controller.asStateFlow()

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
            launch {
                _controller.value = currentRepository.current
            }
            launch {
                m3Repository.all.collect {
                    viewState = viewState.copy(
                        temp = it.state.set.temp,
                        tempPv = it.state.algo.tempPv,
                        fan = it.state.set.fan,
                        isPowerOn = it.state.set.power == 1,
                        isShowAlarms = it.state.alarms != 0
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