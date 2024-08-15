package ru.cityron.presentation.screens.editAlarm

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.all.alarms.GetM3AlarmsUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class EditAlarmViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AlarmsUseCase: GetM3AlarmsUseCase,
) : BaseSharedViewModel<EditAlarmViewState, EditAlarmViewAction, EditAlarmViewIntent>(
    initialState = EditAlarmViewState()
) {

    override fun intent(viewEvent: EditAlarmViewIntent) {
        when (viewEvent) {
            is EditAlarmViewIntent.Launch -> launch(viewEvent.id)
            is EditAlarmViewIntent.OnSaveClick -> onSaveClick()
            is EditAlarmViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is EditAlarmViewIntent.OnActionChange -> onActionChange(viewEvent.value)
            is EditAlarmViewIntent.OnDelayChange -> onDelayChange(viewEvent.value)
            is EditAlarmViewIntent.OnValueChange -> onValueChange(viewEvent.value)
        }
    }

    private fun launch(id: Int) {
        withViewModelScope {
            val alarm = getM3AlarmsUseCase(id)

            viewState = viewState.copy(
                i = id,
                actionOld = alarm.action,
                action = alarm.action,

                delayOld = alarm.delay,
                delay = alarm.delay,
                delayValues = (alarm.delayMin..alarm.delayMax).toList(),

                valueOld = alarm.value,
                value = alarm.value,
                valueValues = (alarm.valueMin..alarm.valueMax).toList()
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onActionChange(value: Int) {
        viewState = viewState.copy(action = value)
        updateIsChanged()
    }

    private fun onDelayChange(value: Int) {
        viewState = viewState.copy(delay = value)
        updateIsChanged()
    }

    private fun onValueChange(value: Int) {
        viewState = viewState.copy(value = value)
        updateIsChanged()
    }

    private fun updateIsChanged() {
        viewState = viewState.copy(
            isChanged = viewState.action != viewState.actionOld
                    || viewState.delay != viewState.delayOld
                    || viewState.value != viewState.valueOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                if (viewState.delay != viewState.delayOld)
                    confRepository.conf("alarm${viewState.i}-delay", viewState.delay)

                if (viewState.value != viewState.valueOld)
                    confRepository.conf("alarm${viewState.i}-value", viewState.value)

                if (viewState.action != viewState.actionOld)
                    confRepository.conf("alarm${viewState.i}-action", viewState.action)

                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = EditAlarmViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}