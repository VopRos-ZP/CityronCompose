package ru.cityron.presentation.screens.algo.other

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.presentation.components.AlgoBooleanItem
import ru.cityron.presentation.components.AlgoNumberItem
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.components.Loader
import ru.cityron.presentation.screens.algo.water.TitledSelectionRow

@Composable
fun AlgoOtherScreen(
    onClick: () -> Unit,
    viewModel: AlgoOtherViewModel = hiltViewModel()
) {
    val stateState = viewModel.state.collectAsState()
    BackScaffold(
        title = "Прочее",
        onClick = onClick,
        bottomBar = {
            if (stateState.value?.isChanged == true) {
                BottomSaveButton(
                    onClick = { viewModel.intent(AlgoOtherViewIntent.OnSaveClick) }
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
                    TitledSelectionRow(
                        labels = stringArrayResource(id = R.array.temp_control),
                        title = "Регулирование температуры",
                        value = state.tempControl,
                        onValueChange = { viewModel.intent(AlgoOtherViewIntent.OnTempControlChange(it)) }
                    )
                    AlgoBooleanItem(
                        text = "Наличие фильтра",
                        value = state.filterEn,
                        onValueChange = { viewModel.intent(AlgoOtherViewIntent.OnFilterEnChange(it)) }
                    )
                    AlgoBooleanItem(
                        text = "Автостарт",
                        value = state.autoStartEn,
                        onValueChange = { viewModel.intent(AlgoOtherViewIntent.OnAutoStartEnChange(it)) }
                    )
                    AlgoBooleanItem(
                        text = "Дистанционное управление",
                        value = state.isDistPower,
                        onValueChange = { viewModel.intent(AlgoOtherViewIntent.OnIsDistPowerChange(it)) }
                    )
                    AlgoNumberItem(
                        text = "Колличество аварийных перезапусков",
                        value = state.alarmRestartCount,
                        onValueChange = { viewModel.intent(AlgoOtherViewIntent.OnAlarmRestartCountChange(it)) }
                    )
                }
            }
        }
    }
    LaunchedEffect(stateState.value) {
        if (stateState.value?.isChanged == false || stateState.value == null) {
            viewModel.intent(AlgoOtherViewIntent.Launch)
        }
    }
}