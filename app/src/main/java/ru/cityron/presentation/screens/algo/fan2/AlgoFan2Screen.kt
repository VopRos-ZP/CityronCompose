package ru.cityron.presentation.screens.algo.fan2

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
fun AlgoFan2Screen(
    onClick: () -> Unit,
    viewModel: AlgoFan2ViewModel = hiltViewModel()
) {
    val stateState = viewModel.state.collectAsState()
    BackScaffold(
        title = "Вытяжной вентилятор",
        onClick = onClick,
        bottomBar = {
            if (stateState.value?.isChanged == true) {
                BottomSaveButton(
                    onClick = { viewModel.intent(AlgoFan2ViewIntent.OnSaveClick) }
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
                        text = "Мин. скорость",
                        textUnit = "%",
                        value = state.fan2SpeedMin,
                        onValueChange = { viewModel.intent(AlgoFan2ViewIntent.OnSpeedMinChange(it)) }
                    )
                    AlgoNumberItem(
                        text = "Макс. скорость",
                        textUnit = "%",
                        value = state.fan2SpeedMax,
                        onValueChange = { viewModel.intent(AlgoFan2ViewIntent.OnSpeedMaxChange(it)) }
                    )
                }
            }
        }
    }
    LaunchedEffect(stateState.value?.isChanged) {
        if (stateState.value?.isChanged == false || stateState.value == null) {
            viewModel.intent(AlgoFan2ViewIntent.Launch)
        }
    }
}