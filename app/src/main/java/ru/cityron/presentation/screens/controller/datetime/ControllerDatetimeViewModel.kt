package ru.cityron.presentation.screens.controller.datetime

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3AllUseCase
import ru.cityron.domain.utils.Time
import ru.cityron.domain.utils.toTimeZone
import ru.cityron.presentation.components.MviViewModel
import javax.inject.Inject

@HiltViewModel
class ControllerDatetimeViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AllUseCase: GetM3AllUseCase,
) : MviViewModel<ControllerDatetimeViewState, ControllerDatetimeViewIntent>() {

    override fun intent(intent: ControllerDatetimeViewIntent) {
        when (intent) {
            is ControllerDatetimeViewIntent.Launch -> launch()
            is ControllerDatetimeViewIntent.OnSaveClick -> onSaveClick()
            is ControllerDatetimeViewIntent.OnTimeFSntpChange -> updateState {
                copy(
                    timeFSntp = intent.value,
                    isChanged = intent.value != timeFSntpOld
                )
            }
            is ControllerDatetimeViewIntent.OnTimeIpChange -> updateState {
                copy(
                    timeIp = intent.value,
                    isChanged = intent.value != timeIpOld
                )
            }
            is ControllerDatetimeViewIntent.OnDateChange -> updateState {
                copy(
                    date = intent.value,
                    isChanged = intent.value != dateOld
                )
            }
            is ControllerDatetimeViewIntent.OnTimeChange -> updateState {
                copy(
                    time = intent.value,
                    isChanged = intent.value != timeOld
                )
            }
            is ControllerDatetimeViewIntent.OnTimeZoneChange -> updateState {
                copy(
                    timeZone = intent.value.toTimeZone(),
                    isChanged = intent.value.toTimeZone() != timeZoneOld
                )
            }
        }
    }

    private fun launch() {
        updateState({ copy() }, ControllerDatetimeViewState())
        scope.launch {
            val all = getM3AllUseCase()
            updateState {
                val zone = all.settings.time.zone
                val datetime = Time.secondsToString(all.state.rtcTime, zone, Time.formatDatetime).split(" ")
                copy(
                    timeFSntpOld = all.settings.time.fSntp,
                    timeFSntp = all.settings.time.fSntp,
                    timeIpOld = all.settings.time.ip,
                    timeIp = all.settings.time.ip,
                    dateOld = datetime[0],
                    date = datetime[0],
                    timeOld = datetime[1],
                    time = datetime[1],
                    timeZoneOld = "GMT${zone.toTimeZone()}",
                    timeZone = "GMT${zone.toTimeZone()}",
                )
            }
        }
    }

    private fun onSaveClick() {
        scope.launch {

        }
    }

}