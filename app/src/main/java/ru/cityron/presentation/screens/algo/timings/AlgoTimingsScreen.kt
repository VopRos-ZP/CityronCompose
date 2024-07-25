package ru.cityron.presentation.screens.algo.timings

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
fun AlgoTimingsScreen(
    onClick: () -> Unit,
    viewModel: AlgoTimingsViewModel = hiltViewModel()
) {
    val stateState = viewModel.state.collectAsState()
    BackScaffold(
        title = "Тайминги",
        onClick = onClick,
        bottomBar = {
            if (stateState.value?.isChanged == true) {
                BottomSaveButton(
                    onClick = { viewModel.intent(AlgoTimingsViewIntent.OnSaveClick) }
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
                        text = "Открытие заслонки",
                        textUnit = "сек",
                        value = state.timeOpenDamper,
                        onValueChange = { viewModel.intent(AlgoTimingsViewIntent.OnTimeOpenDamperChange(it)) }
                    )
                    AlgoNumberItem(
                        text = "Разгон вентилятора",
                        textUnit = "сек",
                        value = state.timeAccelerFan,
                        onValueChange = { viewModel.intent(AlgoTimingsViewIntent.OnTimeAccelerFanChange(it)) }
                    )
                    AlgoNumberItem(
                        text = "Продува ТЭНа",
                        textUnit = "сек",
                        value = state.timeBlowHeat,
                        onValueChange = { viewModel.intent(AlgoTimingsViewIntent.OnTimeBlowHeatChange(it)) }
                    )
                }
            }
        }
    }
    LaunchedEffect(stateState.value) {
        if (stateState.value?.isChanged == false || stateState.value == null) {
            viewModel.intent(AlgoTimingsViewIntent.Launch)
        }
    }
}