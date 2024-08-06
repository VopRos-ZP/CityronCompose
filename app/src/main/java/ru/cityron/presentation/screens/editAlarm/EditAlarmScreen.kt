package ru.cityron.presentation.screens.editAlarm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.presentation.components.BackScaffoldWithState
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.components.Picker
import ru.cityron.presentation.components.rememberSnackbarState
import ru.cityron.presentation.screens.editScheduler.DayChip
import ru.cityron.presentation.screens.editScheduler.TitledContent

@Composable
fun EditAlarmScreen(
    onClick: () -> Unit, id: Int,
    viewModel: EditAlarmViewModel = hiltViewModel()
) {
    val stateState = viewModel.state.collectAsState()
    val alarmsString = stringArrayResource(id = R.array.alarms_m3)
    val snackbarState = rememberSnackbarState(result = stateState.value?.result)
    BackScaffoldWithState(
        title = "Аварии / Настройки",
        onClick = onClick,
        state = stateState,
        snackbarState = snackbarState,
        bottomBar = {
            if (stateState.value?.isChanged == true) {
                BottomSaveButton(onClick = { viewModel.intent(EditAlarmViewIntent.OnSaveClick) })
            }
        },
    ) { state ->
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = alarmsString[state.i - 1],
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                item {
                    ActionRow(
                        action = state.action,
                        onActionChanged = { viewModel.intent(EditAlarmViewIntent.OnActionChange(it)) }
                    )
                }
                if (state.delayValues.isNotEmpty() && state.delayValues.all { it != 0 }) {
                    item {
                        DelayRow(
                            delay = state.delay,
                            values = state.delayValues,
                            onDelayChanged = { viewModel.intent(EditAlarmViewIntent.OnDelayChange(it)) }
                        )
                    }
                }
                if (state.valueValues.isNotEmpty() && state.valueValues.all { it != 0 }) {
                    item {
                        ValueRow(
                            value = state.value,
                            values = state.valueValues,
                            onValueChanged = { viewModel.intent(EditAlarmViewIntent.OnValueChange(it)) }
                        )
                    }
                }
            }
        }
        LaunchedEffect(state) {
            if (state.result != null) {
                snackbarState.showSnackbar {
                    viewModel.intent(EditAlarmViewIntent.OnSnakbarResultChange(null))
                }
            }
        }
    }
    LaunchedEffect(stateState.value?.result) {
        if (stateState.value?.result == null) {
            viewModel.intent(EditAlarmViewIntent.Launch(id))
        }
    }
}

@Composable
fun ActionRow(
    action: Int,
    onActionChanged: (Int) -> Unit
) {
    val labels = listOf("НЕТ", "СТОП", "РЕСТАРТ")
    var selected by remember { mutableIntStateOf(action) }
    TitledContent(title = "Действие") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            labels.mapIndexed { i, label ->
                DayChip(
                    modifier = Modifier.width(110.dp),
                    day = label,
                    isSelected = selected == i,
                    onClick = { selected = i }
                )
            }
        }
    }
    LaunchedEffect(key1 = selected) {
        onActionChanged(selected)
    }
}

@Composable
fun DelayRow(
    delay: Int,
    values: List<Int>,
    onDelayChanged: (Int) -> Unit
) {
    TitledContent(title = "Задержка, сек") {
        Picker(
            modifier = Modifier.fillMaxWidth(0.5f),
            items = values,
            value = delay,
            onValueChanged = onDelayChanged,
            textModifier = Modifier.padding(8.dp),
        )
    }
}

@Composable
fun ValueRow(
    value: Int,
    values: List<Int>,
    onValueChanged: (Int) -> Unit
) {
    TitledContent(title = "Уставка") {
        Picker(
            modifier = Modifier.fillMaxWidth(0.5f),
            items = values,
            value = value,
            onValueChanged = onValueChanged,
            format = { t -> "$t°С" },
            textModifier = Modifier.padding(8.dp),
        )
    }
}