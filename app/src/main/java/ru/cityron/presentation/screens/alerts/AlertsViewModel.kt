package ru.cityron.presentation.screens.alerts

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.usecase.all.state.GetM3StateUseCase
import ru.cityron.domain.utils.utilsBitGet
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val getM3StateUseCase: GetM3StateUseCase
) : BaseSharedViewModel<AlertsViewState, Any, AlertsViewIntent>(
    initialState = AlertsViewState()
) {

    override fun intent(viewEvent: AlertsViewIntent) {
        when (viewEvent) {
            is AlertsViewIntent.OnAlertsArrayChange -> onAlertsArrayChange(viewEvent.value)
        }
    }

    private fun onAlertsArrayChange(titles: List<String>) {
        withViewModelScope {
            getM3StateUseCase.flow.collect {
                viewState = viewState.copy(
                    alerts = titles.filterIndexed { i, _ -> utilsBitGet(it.alarms, i) }
                )
            }
        }
    }

}