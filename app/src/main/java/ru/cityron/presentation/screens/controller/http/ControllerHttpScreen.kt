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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.presentation.components.AlgoBooleanItem
import ru.cityron.presentation.components.BackScaffoldWithState
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.components.PasswordField

@Composable
fun ControllerHttpScreen(
    onClick: () -> Unit,
    viewModel: ControllerHttpViewModel = hiltViewModel()
) {
    val stateState = viewModel.state.collectAsState()
    BackScaffoldWithState(
        title = "Доступ к  веб-интерфейсу",
        onClick = onClick,
        state = stateState,
        bottomBar = {
            if (stateState.value?.isChanged == true) {
                BottomSaveButton {
                    viewModel.intent(ControllerHttpViewIntent.OnSaveClick)
                }
            }
        }
    ) { state ->
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
                    text = "Пароль на чтение",
                    value = state.fP1,
                    onValueChange = { viewModel.intent(ControllerHttpViewIntent.OnFP1Change(it)) }
                )
                AnimatedVisibility(visible = state.fP1 == 1) {
                    PasswordField(
                        modifier = Modifier.fillMaxWidth(),
                        visibility = state.visibilityP1,
                        onVisibilityChange = { viewModel.intent(ControllerHttpViewIntent.OnVisibilityP1Change) },
                        value = state.p1,
                        onValueChange = { viewModel.intent(ControllerHttpViewIntent.OnP1Change(it)) }
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AlgoBooleanItem(
                    text = "Пароль на изменение",
                    value = state.fP2,
                    onValueChange = { viewModel.intent(ControllerHttpViewIntent.OnFP2Change(it)) }
                )
                AnimatedVisibility(visible = state.fP2 == 1) {
                    PasswordField(
                        modifier = Modifier.fillMaxWidth(),
                        visibility = state.visibilityP2,
                        onVisibilityChange = { viewModel.intent(ControllerHttpViewIntent.OnVisibilityP2Change) },
                        value = state.p2,
                        onValueChange = { viewModel.intent(ControllerHttpViewIntent.OnP2Change(it)) }
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.intent(ControllerHttpViewIntent.Launch)
    }
}