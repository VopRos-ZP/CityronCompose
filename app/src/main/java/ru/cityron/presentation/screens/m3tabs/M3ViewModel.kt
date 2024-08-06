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
import ru.cityron.presentation.mvi.MviViewModel
import ru.cityron.presentation.screens.m3temp.M3TempViewIntent
import ru.cityron.presentation.screens.m3temp.M3TempViewState
import javax.inject.Inject

@HiltViewModel
class M3ViewModel @Inject constructor(
    private val currentRepository: CurrentRepository,
    private val m3Repository: M3Repository,
    private val confRepository: ConfRepository,
) : MviViewModel<M3TempViewState, M3TempViewIntent>() {

    private val _controller = MutableStateFlow<Pair<Controller, DataSource>?>(null)
    val controller = _controller.asStateFlow()

    override fun intent(intent: M3TempViewIntent) {
        when (intent) {
            is M3TempViewIntent.Launch -> launch()
            is M3TempViewIntent.OnTempChange -> setTemp(intent.value)
            is M3TempViewIntent.OnFanChange -> setFan(intent.value)
            is M3TempViewIntent.OnIsShowOnOffDialogChange -> updateState {
                copy(isShowOnOffDialog = intent.value)
            }
            is M3TempViewIntent.OnConfirmOnOffClick -> {}
        }
    }

    private fun launch() {
        updateState({ copy() }, M3TempViewState())
        scope.launch {
            launch {
                _controller.value = currentRepository.current
            }
            launch {
                m3Repository.state.collect {
                    updateState {
                        copy(
                            temp = it.set.temp,
                            tempPv = it.algo.tempPv,
                            fan = it.set.fan,
                            isPowerOn = it.set.power == 1,
                            isShowAlarms = it.alarms != 0,
                        )
                    }
                }
            }
        }
    }

    private fun setTemp(temp: Int) {
        scope.launch {
            confRepository.conf("set-temp", temp)
        }
    }

    private fun setFan(fan: Int) {
        scope.launch {
            confRepository.conf("set-fan", fan)
        }
    }

}