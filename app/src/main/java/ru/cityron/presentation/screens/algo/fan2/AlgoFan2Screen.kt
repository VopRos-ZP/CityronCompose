package ru.cityron.presentation.screens.algo.fan2

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
fun AlgoFan2Screen(
    onClick: () -> Unit,
    viewModel: AlgoFan2ViewModel = hiltViewModel()
) {
    val state by viewModel.state().collectAsState()
    val viewAction = viewModel.action().collectAsState(initial = null)
    var snackbarResult by rememberSnackbarResult()
    BackScaffold(
        title = "Вытяжной вентилятор",
        onClick = onClick,
        snackbarResult = snackbarResult,
        onDismissSnackbar = { if (!isError) viewModel.intent(AlgoFan2ViewIntent.OnSnackbarDismiss) },
        bottomBar = {
            if (state.isChanged) {
                BottomSaveButton(enabled = state.fan2SpeedMinInRange && state.fan2SpeedMaxInRange) {
                    viewModel.intent(AlgoFan2ViewIntent.OnSaveClick)
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
                value = state.fan2SpeedMin,
                isError = state.fan2SpeedMinInRange,
                onValueChange = { viewModel.intent(AlgoFan2ViewIntent.OnSpeedMinChange(it)) }
            )
            AlgoNumberItem(
                text = "Макс. скорость",
                textUnit = "%",
                value = state.fan2SpeedMax,
                isError = state.fan2SpeedMaxInRange,
                onValueChange = { viewModel.intent(AlgoFan2ViewIntent.OnSpeedMaxChange(it)) }
            )
        }
    }
    LaunchedEffect(viewAction.value) {
        when (val action = viewAction.value) {
            is AlgoFan2ViewAction.ShowSnackbar -> snackbarResult = action.result
            else -> viewModel.intent(AlgoFan2ViewIntent.Launch)
        }
    }
}