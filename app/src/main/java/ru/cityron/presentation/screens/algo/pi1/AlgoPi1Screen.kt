package ru.cityron.presentation.screens.algo.pi1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.presentation.components.AlgoBooleanItem
import ru.cityron.presentation.components.AlgoNumberItem
import ru.cityron.presentation.components.BackScaffoldWithState
import ru.cityron.presentation.components.BottomSaveButton

@Composable
fun AlgoPi1Screen(
    onClick: () -> Unit,
    viewModel: AlgoPi1ViewModel = hiltViewModel(),
) {
    val stateState = viewModel.state.collectAsState()
    BackScaffoldWithState(
        title = "ПИ регулятор",
        onClick = onClick,
        state = stateState,
        bottomBar = {
            if (stateState.value?.isChanged == true) {
                BottomSaveButton(
                    onClick = { viewModel.intent(AlgoPi1ViewIntent.OnSaveClick) }
                )
            }
        }
    ) { state ->
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
                onValueChange = { viewModel.intent(AlgoPi1ViewIntent.OnPi1KofPChange(it)) }
            )
            AlgoNumberItem(
                text = "Интегральный коэффициент",
                value = state.piKofI,
                onValueChange = { viewModel.intent(AlgoPi1ViewIntent.OnPi1KofIChange(it)) }
            )
            AlgoNumberItem(
                text = "Зона нечувствительности",
                value = state.piErr,
                onValueChange = { viewModel.intent(AlgoPi1ViewIntent.OnPi1ErrChange(it)) }
            )
        }
    }
    LaunchedEffect(stateState.value) {
        if (stateState.value?.isChanged == false || stateState.value == null) {
            viewModel.intent(AlgoPi1ViewIntent.Launch)
        }
    }
}