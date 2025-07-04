package ru.cityron.presentation.screens.algo.electric

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
fun AlgoElectricScreen(
    onClick: () -> Unit,
    viewModel: AlgoElectricViewModel = hiltViewModel()
) {
    val state by viewModel.state().collectAsState()
    val viewAction = viewModel.action().collectAsState(initial = null)
    var snackbarResult by rememberSnackbarResult()
    BackScaffold(
        title = "Электрический нагрев",
        onClick = onClick,
        snackbarResult = snackbarResult,
        onDismissSnackbar = { if (!isError) viewModel.intent(AlgoElectricViewIntent.OnSnackbarDismiss) },
        bottomBar = {
            if (state.isChanged) {
                BottomSaveButton(enabled = state.isInRange) {
                    viewModel.intent(AlgoElectricViewIntent.OnSaveClick)
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
                text = "Период ШИМ",
                value = state.heatPwmPeriod,
                onValueChange = { viewModel.intent(AlgoElectricViewIntent.OnHeatPwmPeriodChange(it)) },
                isError = !state.isInRange
            )
        }
    }
    LaunchedEffect(viewAction.value) {
        when (val action = viewAction.value) {
            is AlgoElectricViewAction.ShowSnackbar -> snackbarResult = action.result
            else -> viewModel.intent(AlgoElectricViewIntent.Launch)
        }
    }
}