package ru.cityron.presentation.screens.algo.fan1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.presentation.components.AlgoNumberItem
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.components.rememberSnackbarResult

@Composable
fun AlgoFan1Screen(
    onClick: () -> Unit,
    viewModel: AlgoFan1ViewModel = hiltViewModel()
) {
    val state by viewModel.state().collectAsState()
    val viewAction = viewModel.action().collectAsState(initial = null)
    var snackbarResult by rememberSnackbarResult()
    BackScaffold(
        title = "Приточный вентилятор",
        onClick = onClick,
        snackbarResult = snackbarResult,
        onDismissSnackbar = { if (!isError) viewModel.intent(AlgoFan1ViewIntent.OnSnackbarDismiss) },
        bottomBar = {
            if (state.isChanged) {
                BottomSaveButton(enabled = state.fan1SpeedMinInRange && state.fan1SpeedMaxInRange) {
                    viewModel.intent(AlgoFan1ViewIntent.OnSaveClick)
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
            AlgoNumberItem(
                text = "Мин. скорость",
                textUnit = "%",
                value = state.fan1SpeedMin,
                isError = !state.fan1SpeedMinInRange,
                onValueChange = { viewModel.intent(AlgoFan1ViewIntent.OnSpeedMinChange(it)) }
            )
            AlgoNumberItem(
                text = "Макс. скорость",
                textUnit = "%",
                value = state.fan1SpeedMax,
                isError = !state.fan1SpeedMaxInRange,
                onValueChange = { viewModel.intent(AlgoFan1ViewIntent.OnSpeedMaxChange(it)) }
            )
        }
    }
    LaunchedEffect(viewAction.value) {
        when (val action = viewAction.value) {
            is AlgoFan1ViewAction.ShowSnackbar -> snackbarResult = action.result
            else -> viewModel.intent(AlgoFan1ViewIntent.Launch)
        }
    }
}