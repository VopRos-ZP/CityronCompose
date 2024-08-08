package ru.cityron.presentation.screens.algo.pi2

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
fun AlgoPi2Screen(
    onClick: () -> Unit,
    viewModel: AlgoPi2ViewModel = hiltViewModel(),
) {
    val state by viewModel.state().collectAsState()
    val viewAction = viewModel.action().collectAsState(initial = null)
    var snackbarResult by rememberSnackbarResult()
    BackScaffold(
        title = "ПИ регулятор 2",
        onClick = onClick,
        snackbarResult = snackbarResult,
        onDismissSnackbar = { if (!isError) viewModel.intent(AlgoPi2ViewIntent.OnSnackbarDismiss) },
        bottomBar = {
            if (state.isChanged) {
                BottomSaveButton(enabled = state.pi2KofPInRange && state.pi2KofIInRange && state.pi2ErrInRange) {
                    viewModel.intent(AlgoPi2ViewIntent.OnSaveClick)
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
                text = "Пропорциональный коэффициент",
                value = state.pi2KofP,
                isError = !state.pi2KofPInRange,
                onValueChange = { viewModel.intent(AlgoPi2ViewIntent.OnPi2KofPChange(it)) }
            )
            AlgoNumberItem(
                text = "Интегральный коэффициент",
                value = state.pi2KofI,
                isError = !state.pi2KofIInRange,
                onValueChange = { viewModel.intent(AlgoPi2ViewIntent.OnPi2KofIChange(it)) }
            )
            AlgoNumberItem(
                text = "Зона нечувствительности",
                value = state.pi2Err,
                isError = !state.pi2ErrInRange,
                onValueChange = { viewModel.intent(AlgoPi2ViewIntent.OnPi2ErrChange(it)) }
            )
        }
    }
    LaunchedEffect(viewAction.value) {
        when (val action = viewAction.value) {
            is AlgoPi2ViewAction.ShowSnackbar -> snackbarResult = action.result
            null -> viewModel.intent(AlgoPi2ViewIntent.Launch)
        }
    }
}