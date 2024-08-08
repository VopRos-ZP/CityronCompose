package ru.cityron.presentation.screens.controller

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.repository.M3Repository
import ru.cityron.domain.usecase.DeleteControllerUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class ControllerSettingsViewModel @Inject constructor(
    private val m3Repository: M3Repository,
    private val currentRepository: CurrentRepository,
    private val deleteControllerUseCase: DeleteControllerUseCase,
) : BaseSharedViewModel<ControllerSettingsViewState, Any, ControllerSettingsViewIntent>(
    initialState = ControllerSettingsViewState()
) {

    override fun intent(viewEvent: ControllerSettingsViewIntent) {
        when (viewEvent) {
            is ControllerSettingsViewIntent.Launch -> launch()
            is ControllerSettingsViewIntent.OnConfirmDeleteClick -> onDeleteClick()
            is ControllerSettingsViewIntent.OnIsShowDeleteDialogChange -> onIsShowDeleteDialogChange(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            m3Repository.all.collect { all ->
                viewState = viewState.copy(
                    rtcTime = all.state.rtcTime,
                    zone = all.settings.time.zone,
                    timeSntp = all.settings.time.fSntp,
                    ipLoc = all.state.ipLoc,
                    ethDhcp = all.settings.eth.fDhcp,
                    httpP1 = all.settings.http.p1,
                    httpP2 = all.settings.http.p2,
                    metricVal = all.settings.metric.valuesBits,
                    metricFrequency = all.settings.metric.frequency,
                )
            }
        }
    }

    private fun onIsShowDeleteDialogChange(value: Boolean) {
        viewState = viewState.copy(isShowDeleteDialog = value)
    }

    private fun onDeleteClick() {
        withViewModelScope {
            deleteControllerUseCase(currentRepository.current!!.first.id)
        }
    }

}