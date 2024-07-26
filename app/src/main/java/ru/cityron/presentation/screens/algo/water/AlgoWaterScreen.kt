package ru.cityron.presentation.screens.algo.water

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.presentation.components.AlgoNumberItem
import ru.cityron.presentation.components.BackScaffoldWithState
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.screens.editScheduler.DayChip
import ru.cityron.presentation.screens.editScheduler.TitledContent

@Composable
fun AlgoWaterScreen(
    onClick: () -> Unit,
    viewModel: AlgoWaterViewModel = hiltViewModel()
) {
    val stateState = viewModel.state.collectAsState()
    BackScaffoldWithState(
        title = "Водяной нагрев",
        onClick = onClick,
        state = stateState,
        bottomBar = {
            if (stateState.value?.isChanged == true) {
                BottomSaveButton(
                    onClick = { viewModel.intent(AlgoWaterViewIntent.OnSaveClick) }
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
            TitledSelectionRow(
                labels = stringArrayResource(id = R.array.mode_zima_leto_source),
                title = "Управление режимом",
                value = state.modeZimaLetoSource,
                onValueChange = { viewModel.intent(AlgoWaterViewIntent.OnModeZimaLetoSourceChange(it)) }
            )
            TitledSelectionRow(
                labels = stringArrayResource(id = R.array.mode_zima_leto_user),
                title = "Режим работы",
                value = state.modeZimaLetoUser,
                onValueChange = { viewModel.intent(AlgoWaterViewIntent.OnModeZimaLetoUserChange(it)) }
            )
            AlgoNumberItem(
                text = "Время прогрева",
                textUnit = "мин",
                value = state.timeWarmUp,
                onValueChange = { viewModel.intent(AlgoWaterViewIntent.OnTimeWarmUpChange(it)) }
            )
            AlgoNumberItem(
                text = "Время разморозки",
                textUnit = "мин",
                value = state.timeDefrost,
                onValueChange = { viewModel.intent(AlgoWaterViewIntent.OnTimeDefrostChange(it)) }
            )
        }
    }
    LaunchedEffect(stateState.value) {
        if (stateState.value?.isChanged == false || stateState.value == null) {
            viewModel.intent(AlgoWaterViewIntent.Launch)
        }
    }
}

@Composable
fun TitledSelectionRow(
    labels: Array<String>,
    title: String,
    value: Int,
    onValueChange: (Int) -> Unit,
) {
    TitledContent(
        title = title,
        paddingValues = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            labels.mapIndexed { i, s ->
                DayChip(
                    modifier = Modifier.width(170.dp),
                    day = s,
                    isSelected = value == i,
                    onClick = { onValueChange(i) }
                )
            }
        }
    }
}