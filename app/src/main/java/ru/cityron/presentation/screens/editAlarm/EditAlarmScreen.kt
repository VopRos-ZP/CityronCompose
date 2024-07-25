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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.components.Loader
import ru.cityron.presentation.components.Picker
import ru.cityron.presentation.screens.editScheduler.DayChip
import ru.cityron.presentation.screens.editScheduler.TitledContent

@Composable
fun EditAlarmScreen(
    onClick: () -> Unit, id: Int,
    viewModel: EditAlarmViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    BackScaffold(
        title = "Аварии / Настройки",
        onClick = onClick,
        bottomBar = {
            if (state.isChanged) {
                BottomSaveButton(onClick = viewModel::onSaveClick)
            }
        },
        content = { EditAlarmScreenContent(viewModel = viewModel, state = state) }
    )
    LaunchedEffect(Unit) {
        viewModel.fetchAlarm(id)
    }
}

@Composable
private fun EditAlarmScreenContent(
    viewModel: EditAlarmViewModel,
    state: EditAlarmViewState,
) {
    val alarmsString = stringArrayResource(id = R.array.alarms_m3)
    when (val alarm = state.localAlarm) {
        null -> Loader()
        else -> Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = alarmsString[alarm.i - 1],
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
                        action = alarm.action,
                        onActionChanged = viewModel::onActionChanged
                    )
                }
                item {
                    DelayRow(
                        delay = alarm.delay,
                        values = state.delayValues,
                        onDelayChanged = viewModel::onDelayChanged
                    )
                }
                if (state.valueValues.all { it != 0 }) {
                    item {
                        ValueRow(
                            value = alarm.value,
                            values = state.valueValues,
                            onValueChanged = viewModel::onValueChanged
                        )
                    }
                }
            }
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
            textStyle = TextStyle(fontSize = 32.sp),
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
            textStyle = TextStyle(fontSize = 32.sp),
            textModifier = Modifier.padding(8.dp),
        )
    }
}