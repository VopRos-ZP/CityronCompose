package ru.cityron.presentation.screens.algo.pi1

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
import ru.cityron.presentation.components.AlgoBooleanItem
import ru.cityron.presentation.components.AlgoNumberItem
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.components.rememberSnackbarResult

@Composable
fun AlgoPi1Screen(
    onClick: () -> Unit,
    viewModel: AlgoPi1ViewModel = hiltViewModel(),
) {
    val state by viewModel.state().collectAsState()
    val viewAction = viewModel.action().collectAsState(initial = null)
    var snackbarResult by rememberSnackbarResult()
    BackScaffold(
        title = "ПИ регулятор",
        onClick = onClick,
        snackbarResult = snackbarResult,
        onDismissSnackbar = { if (!isError) viewModel.intent(AlgoPi1ViewIntent.OnSnackbarDismiss) },
        bottomBar = {
            if (state.isChanged) {
                BottomSaveButton(enabled = state.piKofPInRange && state.piKofIInRange && state.piErrInRange) {
                    viewModel.intent(AlgoPi1ViewIntent.OnSaveClick)
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
            AlgoBooleanItem(
                text = "Авто",
                value = state.piAutoEn,
                onValueChange = { viewModel.intent(AlgoPi1ViewIntent.OnPiAutoEnChange(it)) }
            )
            AlgoNumberItem(
                text = "Пропорциональный коэффициент",
                value = state.piKofP,
                isError = !state.piKofPInRange,
                onValueChange = { viewModel.intent(AlgoPi1ViewIntent.OnPi1KofPChange(it)) }
            )
            AlgoNumberItem(
                text = "Интегральный коэффициент",
                value = state.piKofI,
                isError = !state.piKofIInRange,
                onValueChange = { viewModel.intent(AlgoPi1ViewIntent.OnPi1KofIChange(it)) }
            )
            AlgoNumberItem(
                text = "Зона нечувствительности",
                value = state.piErr,
                isError = !state.piErrInRange,
                onValueChange = { viewModel.intent(AlgoPi1ViewIntent.OnPi1ErrChange(it)) }
            )
        }
    }
    LaunchedEffect(viewAction.value) {
        when (val action = viewAction.value) {
            is AlgoPi1ViewAction.ShowSnackbar -> snackbarResult = action.result
            null -> viewModel.intent(AlgoPi1ViewIntent.Launch)
        }
    }
}