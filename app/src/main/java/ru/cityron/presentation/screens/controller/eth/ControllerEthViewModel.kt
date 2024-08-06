package ru.cityron.presentation.screens.controller.eth

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3AllUseCase
import ru.cityron.presentation.mvi.MviViewModel
import javax.inject.Inject

@HiltViewModel
class ControllerEthViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AllUseCase: GetM3AllUseCase,
) : MviViewModel<ControllerEthViewState, ControllerEthViewIntent>() {

    override fun intent(intent: ControllerEthViewIntent) {
        when (intent) {
            is ControllerEthViewIntent.Launch -> launch()
            is ControllerEthViewIntent.OnSaveClick -> onSaveClick()
            is ControllerEthViewIntent.OnFDhcpChange -> updateState {
                copy(
                    fDhcp = intent.value,
                    isChanged = isChanged || intent.value != fDhcpOld
                )
            }
            is ControllerEthViewIntent.OnIpChange -> updateState {
                copy(
                    ip = intent.value,
                    isChanged = isChanged || intent.value != ipOld
                )
            }
            is ControllerEthViewIntent.OnMaskChange -> updateState {
                copy(
                    mask = intent.value,
                    isChanged = isChanged || intent.value != maskOld
                )
            }
            is ControllerEthViewIntent.OnGwChange -> updateState {
                copy(
                    gw = intent.value,
                    isChanged = isChanged || intent.value != gwOld
                )
            }
            is ControllerEthViewIntent.OnMacChange -> updateState {
                copy(
                    mac = intent.value,
                    isChanged = isChanged || intent.value != macOld
                )
            }
        }
    }

    private fun launch() {
        updateState({ copy() }, ControllerEthViewState())
        scope.launch {
            try {
                val settings = getM3AllUseCase().settings
                updateState {
                    copy(
                        fDhcpOld = settings.eth.fDhcp,
                        fDhcp = settings.eth.fDhcp,

                        ipOld = settings.eth.ip,
                        ip = settings.eth.ip,

                        maskOld = settings.eth.mask,
                        mask = settings.eth.mask,

                        gwOld = settings.eth.gw,
                        gw = settings.eth.gw,

                        macOld = settings.eth.mac2,
                        mac = settings.eth.mac2,
                    )
                }
            }  catch (_: Exception) {}
        }
    }

    private fun onSaveClick() {
        scope.launch {
            try {
                state.value?.let {
                    confRepository.conf("eth-fDhcp", it.fDhcp)
                    confRepository.conf("eth-ip", it.ip)
                    confRepository.conf("eth-mask", it.mask)
                    confRepository.conf("eth-gw", it.gw)
                    confRepository.conf("eth-mac2", it.mac)
                    updateState { copy(isChanged = false) }
                }
            } catch (_: Exception) {
                updateState { copy(isChanged = true) }
            }
        }
    }

}