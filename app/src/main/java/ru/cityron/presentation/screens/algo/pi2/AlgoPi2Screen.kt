package ru.cityron.presentation.screens.algo.pi2

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
import ru.cityron.presentation.components.AlgoNumberItem
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.components.Loader

@Composable
fun AlgoPi2Screen(
    onClick: () -> Unit,
    viewModel: AlgoPi2ViewModel = hiltViewModel(),
) {
    val stateState = viewModel.state.collectAsState()
    BackScaffold(
        title = "ПИ регулятор 2",
        onClick = onClick,
        bottomBar = {
            if (stateState.value?.isChanged == true) {
                BottomSaveButton(
                    onClick = { viewModel.intent(AlgoPi2ViewIntent.OnSaveClick) }
                )
            }
        }
    ) {
        when (val state = stateState.value) {
            null -> Loader()
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    AlgoNumberItem(
                        text = "Пропорциональный коэффициент",
                        value = state.pi2KofP,
                        onValueChange = { viewModel.intent(AlgoPi2ViewIntent.OnPi2KofPChange(it)) }
                    )
                    AlgoNumberItem(
                        text = "Интегральный коэффициент",
                        value = state.pi2KofI,
                        onValueChange = { viewModel.intent(AlgoPi2ViewIntent.OnPi2KofIChange(it)) }
                    )
                    AlgoNumberItem(
                        text = "Зона нечувствительности",
                        value = state.pi2Err,
                        onValueChange = { viewModel.intent(AlgoPi2ViewIntent.OnPi2ErrChange(it)) }
                    )
                }
            }
        }
    }
    LaunchedEffect(stateState.value) {
        if (stateState.value?.isChanged == false || stateState.value == null) {
            viewModel.intent(AlgoPi2ViewIntent.Launch)
        }
    }
}