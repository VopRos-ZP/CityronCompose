package ru.cityron.presentation.screens.algo.fan1

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
import ru.cityron.presentation.components.BackScaffoldWithState
import ru.cityron.presentation.components.BottomSaveButton

@Composable
fun AlgoFan1Screen(
    onClick: () -> Unit,
    viewModel: AlgoFan1ViewModel = hiltViewModel()
) {
    val stateState = viewModel.state.collectAsState()
    BackScaffoldWithState(
        title = "Приточный вентилятор",
        onClick = onClick,
        state = stateState,
        bottomBar = {
            if (stateState.value?.isChanged == true) {
                BottomSaveButton(onClick = { viewModel.intent(AlgoFan1ViewIntent.OnSaveClick) })
            }
        }
    ) { state ->
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
                onValueChange = { viewModel.intent(AlgoFan1ViewIntent.OnSpeedMinChange(it)) }
            )
            AlgoNumberItem(
                text = "Макс. скорость",
                textUnit = "%",
                value = state.fan1SpeedMax,
                onValueChange = { viewModel.intent(AlgoFan1ViewIntent.OnSpeedMaxChange(it)) }
            )
        }
    }
    LaunchedEffect(stateState.value) {
        if (stateState.value?.isChanged == false || stateState.value == null) {
            viewModel.intent(AlgoFan1ViewIntent.Launch)
        }
    }
}