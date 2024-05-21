package ru.cityron.presentation.screens.scheduler

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.domain.model.m3.M3Task
import ru.cityron.domain.utils.Temp
import ru.cityron.presentation.components.BackScaffold

@Composable
fun SchedulersScreen(
    onClick: () -> Unit,
    onTaskClick: (Int) -> Unit,
    viewModel: SchedulersViewModel = hiltViewModel()
) {
    val tasks by viewModel.tasks.collectAsState()
    BackScaffold(
        title = "Планировщик",
        onClick = onClick,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(tasks) {
                ScheduleCard(
                    task = it,
                    onCheckedChange = {},
                    onClick = { onTaskClick(it.i) }
                )
            }
        }
    }
}

@Composable
fun ScheduleCard(
    task: M3Task,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit,
) {
    fun toTime(num: Int): String = if (num < 10) "0$num" else "$num"
    val time = "${toTime(task.hour)}:${toTime(task.min)}"
    // day
    val day = when (task.day) {
        0 -> "пн"
        1 -> "вт"
        2 -> "ср"
        3 -> "чт"
        4 -> "пт"
        5 -> "сб"
        6 -> "вс"
        else -> throw RuntimeException("")
    }
    // mode
    val mode = when (task.mode) {
        0 -> "обогрев"
        1 -> "вентиляция"
        else -> throw RuntimeException("")
    }
    // off/on
    val offOn = when (task.power == 1) {
        true -> "включить"
        else -> "выключить"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${Temp.toGrade(task.temp)}°с")
                Text(text = time)
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.fan),
                        contentDescription = null
                    )
                    Text(text = "${task.fan}")
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = day)
                VDivider()
                Text(text = mode)
                VDivider()
                Text(text = offOn)
            }
        }
        Switch(
            checked = task.on == 1,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.primaryVariant,
                checkedTrackColor = MaterialTheme.colors.primaryVariant,
                uncheckedThumbColor = MaterialTheme.colors.onBackground,
                uncheckedTrackColor = MaterialTheme.colors.primary
            )
        )
    }
}

@Composable
fun VDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onBackground,
    thickness: Dp = 1.dp
) {
    Box(
        modifier
            .fillMaxHeight()
            .width(thickness)
            .background(color = color)
    )
}