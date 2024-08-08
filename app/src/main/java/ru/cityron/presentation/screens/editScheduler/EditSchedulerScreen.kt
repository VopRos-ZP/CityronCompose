package ru.cityron.presentation.screens.editScheduler

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.domain.utils.toTime
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.components.FanSlider
import ru.cityron.presentation.components.Picker
import ru.cityron.presentation.components.rememberSnackbarResult
import ru.cityron.ui.theme.LightGrey

@Composable
fun EditSchedulerScreen(
    onClick: () -> Unit, id: Int,
    viewModel: EditSchedulerViewModel = hiltViewModel()
) {
    val state by viewModel.state().collectAsState()
    val viewAction = viewModel.action().collectAsState(initial = null)
    var snackbarResult by rememberSnackbarResult()
    BackScaffold(
        title = "Планировщик",
        onClick = onClick,
        snackbarResult = snackbarResult,
        onDismissSnackbar = { if (!isError) viewModel.intent(EditSchedulerViewIntent.OnSnackbarDismiss) },
        bottomBar = {
            if (state.isChanged) {
                BottomSaveButton {
                    viewModel.intent(EditSchedulerViewIntent.OnSaveClick)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (state.hourValues.isNotEmpty() && state.minValues.isNotEmpty()) {
                TimePicker(
                    hour = state.hour,
                    hourValues = state.hourValues,
                    onHourChanged = { viewModel.intent(EditSchedulerViewIntent.OnHourChange(it)) },
                    min = state.min,
                    minValues = state.minValues,
                    onMinChanged = { viewModel.intent(EditSchedulerViewIntent.OnMinChange(it)) },
                )
            }
            DayTabRow(
                day = state.day,
                onDayChanged = { viewModel.intent(EditSchedulerViewIntent.OnDayChange(it)) },
            )
            ModeRow(
                mode = state.mode,
                onModeChanged = { viewModel.intent(EditSchedulerViewIntent.OnModeChange(it)) },
            )
            FanRow(
                fan = state.fan,
                onFanChanged = { viewModel.intent(EditSchedulerViewIntent.OnFanChange(it)) },
            )
            if (state.tempValues.isNotEmpty()) {
                TempRow(
                    value = state.temp,
                    values = state.tempValues,
                    onValueChanged = { viewModel.intent(EditSchedulerViewIntent.OnTempChange(it)) },
                )
            }
            PowerRow(
                power = state.power,
                onPowerChange = { viewModel.intent(EditSchedulerViewIntent.OnPowerChange(it)) },
            )
        }
    }
    LaunchedEffect(viewAction.value) {
        when (val action = viewAction.value) {
            is EditSchedulerViewAction.ShowSnackbar -> snackbarResult = action.result
            null -> viewModel.intent(EditSchedulerViewIntent.Launch(id))
        }
    }
}

@Composable
fun TitledContent(
    title: String,
    paddingValues: PaddingValues = PaddingValues(start = 20.dp, end = 20.dp, bottom = 20.dp),
    trailing: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row {
            Text(
                text = title,
                style = MaterialTheme.typography.h3.copy(fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.weight(1f))
            if (trailing != null) {
                trailing()
            }
        }
        content()
    }
}

@Composable
fun TimePicker(
    hour: Int,
    hourValues: List<Int>,
    onHourChanged: (Int) -> Unit,
    min: Int,
    minValues: List<Int>,
    onMinChanged: (Int) -> Unit
) {
    TitledContent(
        title = "Время",
        paddingValues = PaddingValues(20.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(0.6f)) {
            Picker(
                modifier = Modifier.fillMaxWidth(0.5f),
                value = hour,
                items = hourValues,
                format = ::toTime,
                onValueChanged = onHourChanged,
                textModifier = Modifier.padding(8.dp),
            )
            Picker(
                modifier = Modifier.fillMaxWidth(),
                value = min,
                items = minValues,
                format = ::toTime,
                onValueChanged = onMinChanged,
                textModifier = Modifier.padding(8.dp),
            )
        }
    }
}

@Composable
fun DayTabRow(
    day: Int,
    onDayChanged: (Int) -> Unit
) {
    val values = listOf("пн", "вт", "ср", "чт", "пт", "сб", "вс", "", "", "")

    TitledContent(title = "Дата") {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                (0..4).forEach { i ->
                    DayChip(
                        modifier = Modifier.width(67.dp),
                        day = values[i],
                        isSelected = day == i,
                        onClick = { onDayChanged(i) }
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                (5..9).forEach { i ->
                    DayChip(
                        modifier = Modifier.width(67.dp),
                        day = values[i],
                        isSelected = day == i,
                        onClick = { onDayChanged(i) }
                    )
                }
            }
        }
    }
}

@Composable
fun ModeRow(
    mode: Int,
    onModeChanged: (Int) -> Unit,
) {
    val values = listOf("обогрев", "вентиляция")
    var selected by remember(mode) { mutableIntStateOf(mode) }

    TitledContent(title = "Режим") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            values.forEachIndexed { index, s ->
                DayChip(
                    modifier = Modifier.width(158.dp),
                    day = s,
                    isSelected = selected == index,
                    onClick = { selected = index }
                )
            }
        }
    }
    LaunchedEffect(selected) {
        onModeChanged(selected)
    }
}

@Composable
fun DayChip(
    modifier: Modifier = Modifier,
    day: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    isEnabled: Boolean = day.isNotEmpty()
) {
    Box(
        modifier = Modifier.width(IntrinsicSize.Max),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Box(
            modifier = modifier
                .height(44.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    if (isEnabled) MaterialTheme.colors.primary
                    else Color.Transparent
                )
                .clickable(
                    enabled = isEnabled,
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.uppercase(),
                color = if (isSelected) MaterialTheme.colors.onBackground else LightGrey,
                style = MaterialTheme.typography.h3.copy(fontSize = 14.sp)
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.5f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    .background(MaterialTheme.colors.primaryVariant)
            )
        }
    }
}

@Composable
fun FanRow(
    fan: Int,
    onFanChanged: (Int) -> Unit
) {
    TitledContent(
        title = "Скорость",
        trailing = { Text(text = "$fan") }
    ) {
        FanSlider(
            value = fan,
            onFinishChange = onFanChanged,
            values = listOf(1, 2, 3, 4, 5)
        )
    }
}

@Composable
fun TempRow(
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

@Composable
fun PowerRow(
    power: Int,
    onPowerChange: (Int) -> Unit
) {
    val values = listOf("отключить", "включить")
    var selected by remember(power) { mutableIntStateOf(power) }

    TitledContent(title = "Действие") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            values.forEachIndexed { index, s ->
                DayChip(
                    modifier = Modifier.width(158.dp),
                    day = s,
                    isSelected = selected == index,
                    onClick = { selected = index }
                )
            }
        }
    }
    LaunchedEffect(selected) {
        onPowerChange(selected)
    }
}