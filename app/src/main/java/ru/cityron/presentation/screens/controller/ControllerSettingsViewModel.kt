package ru.cityron.presentation.screens.controller

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.M3Repository
import ru.cityron.presentation.components.MviViewModel
import javax.inject.Inject

@HiltViewModel
class ControllerSettingsViewModel @Inject constructor(
    private val m3Repository: M3Repository
) : MviViewModel<ControllerSettingsViewState, ControllerSettingsViewIntent>() {

    override fun intent(intent: ControllerSettingsViewIntent) {
        when (intent) {
            is ControllerSettingsViewIntent.Launch -> launch()
            is ControllerSettingsViewIntent.OnDeleteControllerClick -> onDeleteClick()
        }
    }

    private fun launch() {
        updateState({ copy() }, ControllerSettingsViewState())
        scope.launch {
            launch {
                m3Repository.settings.collect { settings ->
                    updateState {
                        copy(
                            zone = settings.time.zone,
                            timeSntp = settings.time.fSntp,
                            ethDhcp = settings.eth.fDhcp,
                            httpP1 = settings.http.p1,
                            httpP2 = settings.http.p2,
                            metricVal = settings.metric.valuesBits,
                            metricFrequency = settings.metric.frequency,
                        )
                    }
                }
            }
            launch {
                m3Repository.state.collect { state ->
                    updateState {
                        copy(
                            rtcTime = state.rtcTime,
                            ipLoc = state.ipLoc
                        )
                    }
                }
            }
        }
    }

    private fun onDeleteClick() {

    }

}