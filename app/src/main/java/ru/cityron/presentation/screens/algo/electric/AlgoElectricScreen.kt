package ru.cityron.presentation.screens.algo.electric

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
fun AlgoElectricScreen(
    onClick: () -> Unit,
    viewModel: AlgoElectricViewModel = hiltViewModel()
) {
    val stateState = viewModel.state.collectAsState()
    BackScaffoldWithState(
        title = "Электрический нагрев",
        onClick = onClick,
        state = stateState,
        bottomBar = {
            if (stateState.value?.isChanged == true) {
                BottomSaveButton(
                    onClick = { viewModel.intent(AlgoElectricViewIntent.OnSaveClick) }
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
            AlgoNumberItem(
                text = "Период ШИМ",
                value = state.heatPwmPeriod,
                onValueChange = { viewModel.intent(AlgoElectricViewIntent.OnHeatPwmPeriodChange(it)) }
            )
        }
    }
    LaunchedEffect(stateState.value) {
        if (stateState.value?.isChanged == false || stateState.value == null) {
            viewModel.intent(AlgoElectricViewIntent.Launch)
        }
    }
}