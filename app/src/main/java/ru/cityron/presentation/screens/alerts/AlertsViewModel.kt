package ru.cityron.presentation.screens.alerts

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.repository.M3Repository
import ru.cityron.domain.utils.utilsBitGet
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val m3Repository: M3Repository
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
            m3Repository.all.collect { all ->
                viewState = viewState.copy(
                    alerts = titles.filterIndexed { i, _ -> utilsBitGet(all.state.alarms, i) }
                )
            }
        }
    }

}