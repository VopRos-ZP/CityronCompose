package ru.cityron.presentation.screens.algo.timings

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
fun AlgoTimingsScreen(
    onClick: () -> Unit,
    viewModel: AlgoTimingsViewModel = hiltViewModel()
) {
    val state by viewModel.state().collectAsState()
    val viewAction = viewModel.action().collectAsState(initial = null)
    var snackbarResult by rememberSnackbarResult()
    BackScaffold(
        title = "Тайминги",
        onClick = onClick,
        snackbarResult = snackbarResult,
        onDismissSnackbar = { if (!isError) viewModel.intent(AlgoTimingsViewIntent.OnSnackbarDismiss) },
        bottomBar = {
            if (state.isChanged) {
                BottomSaveButton(enabled = state.timeOpenDamperInRange && state.timeAccelerFanInRange && state.timeBlowHeatInRange) {
                    viewModel.intent(AlgoTimingsViewIntent.OnSaveClick)
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
                text = "Открытие заслонки",
                textUnit = "сек",
                value = state.timeOpenDamper,
                isError = !state.timeOpenDamperInRange,
                onValueChange = { viewModel.intent(AlgoTimingsViewIntent.OnTimeOpenDamperChange(it)) }
            )
            AlgoNumberItem(
                text = "Разгон вентилятора",
                textUnit = "сек",
                value = state.timeAccelerFan,
                isError = !state.timeAccelerFanInRange,
                onValueChange = { viewModel.intent(AlgoTimingsViewIntent.OnTimeAccelerFanChange(it)) }
            )
            AlgoNumberItem(
                text = "Продува ТЭНа",
                textUnit = "сек",
                value = state.timeBlowHeat,
                isError = !state.timeBlowHeatInRange,
                onValueChange = { viewModel.intent(AlgoTimingsViewIntent.OnTimeBlowHeatChange(it)) }
            )
        }
    }
    LaunchedEffect(viewAction.value) {
        when (val action = viewAction.value) {
            is AlgoTimingsViewAction.ShowSnackbar -> snackbarResult = action.result
            null -> viewModel.intent(AlgoTimingsViewIntent.Launch)
        }
    }
}