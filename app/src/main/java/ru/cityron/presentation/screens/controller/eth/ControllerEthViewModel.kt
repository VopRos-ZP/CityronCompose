package ru.cityron.presentation.screens.controller.eth

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.all.settings.eth.GetM3SettingsEthUseCase
import ru.cityron.domain.utils.isValidIPAddress
import ru.cityron.domain.utils.isValidMac
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class ControllerEthViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SettingsEthUseCase: GetM3SettingsEthUseCase,
) : BaseSharedViewModel<ControllerEthViewState, ControllerEthViewAction, ControllerEthViewIntent>(
    initialState = ControllerEthViewState()
) {

    override fun intent(viewEvent: ControllerEthViewIntent) {
        when (viewEvent) {
            is ControllerEthViewIntent.Launch -> launch()
            is ControllerEthViewIntent.OnSaveClick -> onSaveClick()
            is ControllerEthViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is ControllerEthViewIntent.OnFDhcpChange -> onFDhcpChange(viewEvent.value)
            is ControllerEthViewIntent.OnIpChange -> onIpChange(viewEvent.value)
            is ControllerEthViewIntent.OnMaskChange -> onMaskChange(viewEvent.value)
            is ControllerEthViewIntent.OnGwChange -> onGwChange(viewEvent.value)
            is ControllerEthViewIntent.OnMacChange -> onMacChange(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            val eth = getM3SettingsEthUseCase()
            viewState = viewState.copy(
                fDhcpOld = eth.fDhcp,
                fDhcp = eth.fDhcp,

                ipOld = eth.ip,
                ip = eth.ip,

                maskOld = eth.mask,
                mask = eth.mask,

                gwOld = eth.gw,
                gw = eth.gw,

                macOld = eth.mac2,
                mac = eth.mac2,
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onFDhcpChange(value: Int) {
        viewState = viewState.copy(fDhcp = value)
        updateIsChanged()
    }

    private fun onIpChange(value: String) {
        viewState = viewState.copy(
            ip = value,
            ipIsCorrect = value.isValidIPAddress(),
        )
        updateIsChanged()
    }

    private fun onMaskChange(value: String) {
        viewState = viewState.copy(
            mask = value,
            maskIsCorrect = value.isValidIPAddress(),
        )
        updateIsChanged()
    }

    private fun onGwChange(value: String) {
        viewState = viewState.copy(
            gw = value,
            gwIsCorrect = value.isValidIPAddress(),
        )
        updateIsChanged()
    }

    private fun onMacChange(value: String) {
        viewState = viewState.copy(
            mac = value,
            macIsCorrect = value.isValidMac()
        )
        updateIsChanged()
    }

    private fun updateIsChanged() {
        viewState = viewState.copy(
            isChanged = viewState.mac != viewState.macOld
                    || viewState.fDhcp != viewState.fDhcpOld
                    || viewState.mask != viewState.maskOld
                    || viewState.gw != viewState.gwOld
                    || viewState.ip != viewState.ipOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                if (viewState.fDhcp != viewState.fDhcpOld)
                    confRepository.conf("eth-fDhcp", viewState.fDhcp)

                if (viewState.ip != viewState.ipOld)
                    confRepository.conf("eth-ip", viewState.ip)

                if (viewState.mask != viewState.maskOld)
                    confRepository.conf("eth-mask", viewState.mask)

                if (viewState.gw != viewState.gwOld)
                    confRepository.conf("eth-gw", viewState.gw)

                if (viewState.mac != viewState.macOld)
                    confRepository.conf("eth-mac2", viewState.mac)

                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = ControllerEthViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}