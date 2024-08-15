package ru.cityron.presentation.screens.editScheduler

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.all.sched.GetM3TasksUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class EditSchedulerViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3TasksUseCase: GetM3TasksUseCase,
) : BaseSharedViewModel<EditSchedulerViewState, EditSchedulerViewAction, EditSchedulerViewIntent>(
    initialState = EditSchedulerViewState()
) {

    override fun intent(viewEvent: EditSchedulerViewIntent) {
        when (viewEvent) {
            is EditSchedulerViewIntent.Launch -> launch(viewEvent.value)
            is EditSchedulerViewIntent.OnSaveClick -> onSaveClick()
            is EditSchedulerViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is EditSchedulerViewIntent.OnHourChange -> onHourChanged(viewEvent.value)
            is EditSchedulerViewIntent.OnMinChange -> onMinChanged(viewEvent.value)
            is EditSchedulerViewIntent.OnDayChange -> onDayChanged(viewEvent.value)
            is EditSchedulerViewIntent.OnModeChange -> onModeChanged(viewEvent.value)
            is EditSchedulerViewIntent.OnFanChange -> onFanChanged(viewEvent.value)
            is EditSchedulerViewIntent.OnTempChange -> onTempChanged(viewEvent.value)
            is EditSchedulerViewIntent.OnPowerChange -> onPowerChanged(viewEvent.value)
        }
    }

    private fun launch(id: Int) {
        withViewModelScope {
            val task = getM3TasksUseCase(id)

            viewState = viewState.copy(
                id = id,

                hourOld = task.hour,
                hour = task.hour,
                hourValues = (0..23).toList(),

                minOld = task.min,
                min = task.min,
                minValues = (0..59).toList(),

                dayOld = task.day,
                day = task.day,

                modeOld = task.mode,
                mode = task.mode,

                fanOld = task.fan,
                fan = task.fan,

                tempOld = task.temp,
                temp = task.temp,
                tempValues = (5..40).toList(),

                powerOld = task.power,
                power = task.power,
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onHourChanged(value: Int) {
        viewState = viewState.copy(hour = value)
        updateIsChanged()
    }

    private fun onMinChanged(value: Int) {
        viewState = viewState.copy(min = value)
        updateIsChanged()
    }

    private fun onDayChanged(value: Int) {
        viewState = viewState.copy(day = value)
        updateIsChanged()
    }

    private fun onModeChanged(value: Int) {
        viewState = viewState.copy(mode = value)
        updateIsChanged()
    }

    private fun onFanChanged(value: Int) {
        viewState = viewState.copy(fan = value)
        updateIsChanged()
    }

    private fun onTempChanged(value: Int) {
        viewState = viewState.copy(temp = value)
        updateIsChanged()
    }

    private fun onPowerChanged(value: Int) {
        viewState = viewState.copy(power = value)
        updateIsChanged()
    }

    private fun updateIsChanged() {
        viewState = viewState.copy(
            isChanged = viewState.power != viewState.powerOld
                    || viewState.hour != viewState.hourOld
                    || viewState.day != viewState.dayOld
                    || viewState.mode != viewState.modeOld
                    || viewState.fan != viewState.fanOld
                    || viewState.temp != viewState.tempOld
                    || viewState.min != viewState.minOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                if (viewState.hour != viewState.hourOld)
                    confRepository.conf("task${viewState.id}-hour", viewState.hour)

                if (viewState.min != viewState.minOld)
                    confRepository.conf("task${viewState.id}-min", viewState.min)

                if (viewState.day != viewState.dayOld)
                    confRepository.conf("task${viewState.id}-day", viewState.day)

                if (viewState.mode != viewState.modeOld)
                    confRepository.conf("task${viewState.id}-mode", viewState.mode)

                if (viewState.fan != viewState.fanOld)
                    confRepository.conf("task${viewState.id}-fan", viewState.fan)

                if (viewState.temp != viewState.tempOld)
                    confRepository.conf("task${viewState.id}-temp", viewState.temp)

                if (viewState.power != viewState.powerOld)
                    confRepository.conf("task${viewState.id}-power", viewState.power)

                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = EditSchedulerViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}