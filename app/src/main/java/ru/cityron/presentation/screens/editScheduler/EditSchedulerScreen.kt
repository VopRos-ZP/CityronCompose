package ru.cityron.presentation.screens.editScheduler

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.domain.utils.toTime
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.FanSlider
import ru.cityron.presentation.components.Picker

@Composable
fun EditSchedulerScreen(
    onClick: () -> Unit, id: Int,
    viewModel: EditSchedulerViewModel =  hiltViewModel()
) {
    val task by viewModel.localTask.collectAsState()

    BackScaffold(
        title = "Планировщик",
        onClick = onClick
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                TitledContent(title = "Время") {
                    TimePicker(
                        hour = task.hour,
                        onHourChanged = {},
                        min = task.min,
                        onMinChanged = {}
                    )
                }
            }
            item {
                TitledContent(title = "Дата") {
                    DayTabRow(
                        day = task.day,
                        onDayChanged = {}
                    )
                }
            }
            item {
                TitledContent(title = "Режим") {
                    ModeRow(
                        mode = task.mode,
                        onModeChanged = {}
                    )
                }
            }
            item {
                FanRow(
                    fan = task.fan,
                    onFanChanged = {}
                )
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchTask(id)
    }
}

@Composable
fun TitledContent(
    title: String,
    trailing: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
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
    onHourChanged: (Int) -> Unit,
    min: Int,
    onMinChanged: (Int) -> Unit
) {
    val hourValues = (0..23).toList()
    val minValues = (0..59).toList()

    Row(modifier = Modifier.fillMaxWidth(0.5f)) {
        Picker(
            items = hourValues,
            value = hour,
            onValueChange = onHourChanged,
            format = ::toTime,
            textModifier = Modifier.padding(8.dp),
            modifier = Modifier.fillMaxWidth(0.5f),
            dividerColor = MaterialTheme.colors.onBackground,
            textStyle = TextStyle(fontSize = 32.sp),
        )
        Picker(
            items = minValues,
            value = min,
            onValueChange = onMinChanged,
            format = ::toTime,
            textModifier = Modifier.padding(8.dp),
            modifier = Modifier.fillMaxWidth(),
            dividerColor = MaterialTheme.colors.onBackground,
            textStyle = TextStyle(fontSize = 32.sp),
        )
    }
}

@Composable
fun DayTabRow(
    day: Int,
    onDayChanged: (Int) -> Unit
) {
    val values = listOf("пн", "вт", "ср", "чт", "пт", "сб", "вс", "", "", "")
    var selected by remember { mutableIntStateOf(day) }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (0..4).forEach { i ->
                DayChip(
                    modifier = Modifier.size(67.dp, 44.dp),
                    day = values[i],
                    isSelected = selected == i,
                    onClick = { selected = i }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (5..9).forEach { i ->
                DayChip(
                    modifier = Modifier.size(67.dp, 44.dp),
                    day = values[i],
                    isSelected = selected == i,
                    onClick = { selected = i }
                )
            }
        }
    }
    LaunchedEffect(key1 = selected) {
        onDayChanged(selected)
    }
}

@Composable
fun ModeRow(
    mode: Int,
    onModeChanged: (Int) -> Unit,
) {
    val values = listOf("обогрев", "вентиляция")
    var selected by remember { mutableIntStateOf(mode) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        values.forEachIndexed { index, s ->
            DayChip(
                modifier = Modifier.size(158.dp, 44.dp),
                day = s,
                isSelected = selected == index,
                onClick = { selected = index }
            )
        }
    }
    LaunchedEffect(key1 = selected) {
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
                color = MaterialTheme.colors.onBackground,
                fontSize = 14.sp,
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
    var float by remember(fan) { mutableIntStateOf(fan) }
    TitledContent(
        title = "Скорость",
        trailing = { 
            Text(text = "$float")
        }
    ) {
        FanSlider(
            fan = float,
            onFanChange = { float = it },
            onFanChangeFinished = onFanChanged
        )
    }
}