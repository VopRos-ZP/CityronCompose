package ru.cityron.presentation.screens.controller.http

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.presentation.components.AlgoBooleanItem
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.components.PasswordField
import ru.cityron.presentation.components.rememberSnackbarResult

@Composable
fun ControllerHttpScreen(
    onClick: () -> Unit,
    viewModel: ControllerHttpViewModel = hiltViewModel()
) {
    val state by viewModel.state().collectAsState()
    val viewAction = viewModel.action().collectAsState(initial = null)
    var snackbarResult by rememberSnackbarResult()
    BackScaffold(
        title = "Доступ к  веб-интерфейсу",
        onClick = onClick,
        snackbarResult = snackbarResult,
        onDismissSnackbar = { if (!isError) viewModel.intent(ControllerHttpViewIntent.OnSnackbarDismiss) },
        bottomBar = {
            if (state.isChanged) {
                BottomSaveButton {
                    viewModel.intent(ControllerHttpViewIntent.OnSaveClick)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Длина пароля должна быть от 4 до 8 символов, допустимые символы: 0-9, A-Z, a-z",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AlgoBooleanItem(
                    text = "Пароль Просмотр",
                    value = state.fPr,
                    onValueChange = { viewModel.intent(ControllerHttpViewIntent.OnFPrChange(it)) }
                )
                AnimatedVisibility(visible = state.fPr == 1) {
                    PasswordField(
                        modifier = Modifier.fillMaxWidth(),
                        visibility = state.visibilityPr,
                        onVisibilityChange = { viewModel.intent(ControllerHttpViewIntent.OnVisibilityPrChange) },
                        value = state.pr,
                        onValueChange = { viewModel.intent(ControllerHttpViewIntent.OnPrChange(it)) }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AlgoBooleanItem(
                    text = "Пароль Пользователь",
                    value = state.fPu,
                    onValueChange = { viewModel.intent(ControllerHttpViewIntent.OnFPuChange(it)) }
                )
                AnimatedVisibility(visible = state.fPu == 1) {
                    PasswordField(
                        modifier = Modifier.fillMaxWidth(),
                        visibility = state.visibilityPu,
                        onVisibilityChange = { viewModel.intent(ControllerHttpViewIntent.OnVisibilityPuChange) },
                        value = state.pu,
                        onValueChange = { viewModel.intent(ControllerHttpViewIntent.OnPuChange(it)) }
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AlgoBooleanItem(
                    text = "Пароль Администратор",
                    value = state.fPw,
                    onValueChange = { viewModel.intent(ControllerHttpViewIntent.OnFPwChange(it)) }
                )
                AnimatedVisibility(visible = state.fPw == 1) {
                    PasswordField(
                        modifier = Modifier.fillMaxWidth(),
                        visibility = state.visibilityPw,
                        onVisibilityChange = { viewModel.intent(ControllerHttpViewIntent.OnVisibilityPwChange) },
                        value = state.pw,
                        onValueChange = { viewModel.intent(ControllerHttpViewIntent.OnPwChange(it)) }
                    )
                }
            }
        }
    }
    LaunchedEffect(viewAction.value) {
        when (val action = viewAction.value) {
            is ControllerHttpViewAction.ShowSnackbar -> snackbarResult = action.result
            null -> viewModel.intent(ControllerHttpViewIntent.Launch)
        }
    }
}