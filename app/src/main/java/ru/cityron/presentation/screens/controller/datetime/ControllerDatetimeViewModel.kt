package ru.cityron.presentation.screens.controller.datetime

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.all.settings.time.GetM3SettingsTimeUseCase
import ru.cityron.domain.usecase.all.state.GetM3StateUseCase
import ru.cityron.domain.utils.Time
import ru.cityron.domain.utils.Time.stringToSeconds
import ru.cityron.domain.utils.isValidDate
import ru.cityron.domain.utils.isValidIPAddress
import ru.cityron.domain.utils.isValidTime
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class ControllerDatetimeViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3StateUseCase: GetM3StateUseCase,
    private val getM3SettingsTimeUseCase: GetM3SettingsTimeUseCase,
) : BaseSharedViewModel<ControllerDatetimeViewState, ControllerDatetimeViewAction, ControllerDatetimeViewIntent>(
    initialState = ControllerDatetimeViewState()
) {

    override fun intent(viewEvent: ControllerDatetimeViewIntent) {
        when (viewEvent) {
            is ControllerDatetimeViewIntent.Launch -> launch()
            is ControllerDatetimeViewIntent.OnSaveClick -> onSaveClick()
            is ControllerDatetimeViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is ControllerDatetimeViewIntent.OnTimeFSntpChange -> onTimeFSntpChange(viewEvent.value)
            is ControllerDatetimeViewIntent.OnTimeIpChange -> onTimeIpChange(viewEvent.value)
            is ControllerDatetimeViewIntent.OnDateChange -> onDateChange(viewEvent.value)
            is ControllerDatetimeViewIntent.OnTimeChange -> onTimeChange(viewEvent.value)
            is ControllerDatetimeViewIntent.OnTimeZoneChange -> onTimeZoneChange(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            val time = getM3SettingsTimeUseCase()
            val state = getM3StateUseCase()
            val zone = time.zone
            val datetime = Time.secondsToString(state.rtcTime, zone, Time.formatDatetime).split(" ")

            viewState = viewState.copy(
                timeFSntpOld = time.fSntp,
                timeFSntp = time.fSntp,

                timeIpOld = time.ip,
                timeIp = time.ip,
                timeIpIsCorrect = time.ip.isValidIPAddress(),

                dateOld = datetime[0],
                date = datetime[0],
                dateIsCorrect = datetime[0].isValidDate(),

                timeOld = datetime[1],
                time = datetime[1],
                timeIsCorrect = datetime[1].isValidTime(),

                timeZoneOld = zone,
                timeZone = zone,
                timeZoneRange = (time.zoneMin..time.zoneMax)
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onTimeFSntpChange(value: Int) {
        viewState = viewState.copy(timeFSntp = value)
        updateIsChanged()
    }

    private fun onTimeIpChange(value: String) {
        viewState = viewState.copy(
            timeIp = value,
            timeIpIsCorrect = value.isValidIPAddress(),
        )
        updateIsChanged()
    }

    private fun onDateChange(value: String) {
        viewState = viewState.copy(
            date = value,
            dateIsCorrect = value.isValidDate(),
        )
        updateIsChanged()
    }

    private fun onTimeChange(value: String) {
        viewState = viewState.copy(
            time = value,
            timeIsCorrect = value.isValidTime(),
        )
        updateIsChanged()
    }

    private fun onTimeZoneChange(value: Int) {
        viewState = viewState.copy(timeZone = value)
        updateIsChanged()
    }

    private fun updateIsChanged() {
        viewState = viewState.copy(
            isChanged = viewState.timeZone != viewState.timeZoneOld
                    || viewState.timeIp != viewState.timeIpOld
                    || viewState.date != viewState.dateOld
                    || viewState.time != viewState.timeOld
                    || viewState.timeFSntp != viewState.timeFSntpOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                confRepository.conf("time-fSntp", viewState.timeFSntp)
                confRepository.conf("time-ip", viewState.timeIp)
                confRepository.conf("time-zone", viewState.timeZone)
                confRepository.conf("time-unix", stringToSeconds("${viewState.date} ${viewState.time}", viewState.timeZone, Time.formatDatetime))
                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = ControllerDatetimeViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}