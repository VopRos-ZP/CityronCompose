package ru.cityron.presentation.screens.controller

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.usecase.controller.DeleteControllerUseCase
import ru.cityron.domain.usecase.all.settings.eth.GetM3SettingsEthUseCase
import ru.cityron.domain.usecase.all.settings.http.GetM3SettingsHttpUseCase
import ru.cityron.domain.usecase.all.settings.metric.GetM3SettingsMetricUseCase
import ru.cityron.domain.usecase.all.settings.time.GetM3SettingsTimeUseCase
import ru.cityron.domain.usecase.all.state.GetM3StateUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class ControllerSettingsViewModel @Inject constructor(
    private val getM3StateUseCase: GetM3StateUseCase,
    private val getM3SettingsTimeUseCase: GetM3SettingsTimeUseCase,
    private val getM3SettingsMetricUseCase: GetM3SettingsMetricUseCase,
    private val getM3SettingsEthUseCase: GetM3SettingsEthUseCase,
    private val getM3SettingsHttpUseCase: GetM3SettingsHttpUseCase,
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
            launch {
                getM3StateUseCase.flow.collect {
                    viewState = viewState.copy(
                        rtcTime = it.rtcTime,
                        ipLoc = it.ipLoc
                    )
                }
            }
            launch {
                getM3SettingsHttpUseCase.flow.collect {
                    viewState = viewState.copy(
                        httpPr = it.pr,
                        httpPu = it.pu,
                        httpPw = it.pw,
                    )
                }
            }
            launch {
                getM3SettingsEthUseCase.flow.collect {
                    viewState = viewState.copy(
                        ethDhcp = it.fDhcp
                    )
                }
            }
            launch {
                getM3SettingsTimeUseCase.flow.collect {
                    viewState = viewState.copy(
                        zone = it.zone,
                        timeSntp = it.fSntp,
                    )
                }
            }
            launch {
                getM3SettingsMetricUseCase.flow.collect {
                    viewState = viewState.copy(
                        metricVal = it.valuesBits,
                        metricFrequency = it.frequency,
                    )
                }
            }
        }
    }

    private fun onIsShowDeleteDialogChange(value: Boolean) {
        viewState = viewState.copy(isShowDeleteDialog = value)
    }

    private fun onDeleteClick() {
        withViewModelScope {
            currentRepository.current.value?.let {
                deleteControllerUseCase(it.id)
                currentRepository.setCurrentController(null)
            }
        }
    }

}