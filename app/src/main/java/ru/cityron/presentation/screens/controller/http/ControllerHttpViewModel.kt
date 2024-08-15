package ru.cityron.presentation.screens.controller.http

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.all.settings.http.GetM3SettingsHttpUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class ControllerHttpViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SettingsHttpUseCase: GetM3SettingsHttpUseCase,
) : BaseSharedViewModel<ControllerHttpViewState, ControllerHttpViewAction, ControllerHttpViewIntent>(
    initialState = ControllerHttpViewState()
) {

    override fun intent(viewEvent: ControllerHttpViewIntent) {
        when (viewEvent) {
            is ControllerHttpViewIntent.Launch -> launch()
            is ControllerHttpViewIntent.OnSaveClick -> onSaveClick()
            is ControllerHttpViewIntent.OnSnackbarDismiss -> onSnackbarDismiss()
            is ControllerHttpViewIntent.OnVisibilityPrChange -> onVisibilityPrChange()
            is ControllerHttpViewIntent.OnVisibilityPuChange -> onVisibilityPuChange()
            is ControllerHttpViewIntent.OnVisibilityPwChange -> onVisibilityPwChange()
            is ControllerHttpViewIntent.OnFPrChange -> onFPrChange(viewEvent.value)
            is ControllerHttpViewIntent.OnFPuChange -> onFPuChange(viewEvent.value)
            is ControllerHttpViewIntent.OnFPwChange -> onFPwChange(viewEvent.value)
            is ControllerHttpViewIntent.OnPrChange -> onPrChange(viewEvent.value)
            is ControllerHttpViewIntent.OnPuChange -> onPuChange(viewEvent.value)
            is ControllerHttpViewIntent.OnPwChange -> onPwChange(viewEvent.value)
        }
    }

    private fun launch() {
        withViewModelScope {
            val http = getM3SettingsHttpUseCase()
            viewState = viewState.copy(
                fPr = http.fPr,
                fPu = http.fPu,
                fPw = http.fPw,

                prOld = http.pr,
                pr = http.pr,

                puOld = http.pu,
                pu = http.pu,

                pwOld = http.pw,
                pw = http.pw,
            )
        }
    }

    private fun onSnackbarDismiss() {
        viewAction = null
    }

    private fun onVisibilityPrChange() {
        viewState = viewState.copy(visibilityPr = !viewState.visibilityPr)
    }

    private fun onVisibilityPuChange() {
        viewState = viewState.copy(visibilityPu = !viewState.visibilityPu)
    }

    private fun onVisibilityPwChange() {
        viewState = viewState.copy(visibilityPw = !viewState.visibilityPw)
    }

    private fun onFPrChange(value: Int) {
        viewState = viewState.copy(fPr = value)
    }

    private fun onFPuChange(value: Int) {
        viewState = viewState.copy(fPu = value)
    }

    private fun onFPwChange(value: Int) {
        viewState = viewState.copy(fPw = value)
    }

    private fun onPrChange(value: String) {
        viewState = viewState.copy(pr = value)
        updateIsChanged()
    }

    private fun onPuChange(value: String) {
        viewState = viewState.copy(pu = value)
        updateIsChanged()
    }

    private fun onPwChange(value: String) {
        viewState = viewState.copy(pw = value)
        updateIsChanged()
    }

    private fun updateIsChanged() {
        viewState = viewState.copy(
            isChanged = viewState.pr != viewState.prOld
                    || viewState.pu != viewState.puOld
                    || viewState.pw != viewState.pwOld
        )
    }

    private fun onSaveClick() {
        withViewModelScope {
            val (label, isError) = try {
                confRepository.conf("http-fPr", viewState.fPr)
                confRepository.conf("http-fPu", viewState.fPu)
                confRepository.conf("http-fPw", viewState.fPw)

                if (viewState.pr != viewState.prOld)
                    confRepository.conf("http-Pu", viewState.pr)

                if (viewState.pu != viewState.puOld)
                    confRepository.conf("http-Pu", viewState.pu)

                if (viewState.pw != viewState.pwOld)
                    confRepository.conf("http-Pw", viewState.pw)

                R.string.success_save_settings to false
            } catch (_: Exception) {
                R.string.error_save_settings to true
            }
            viewAction = ControllerHttpViewAction.ShowSnackbar(SnackbarResult(label, isError))
            viewState = viewState.copy(isChanged = isError)
        }
    }

}