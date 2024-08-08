package ru.cityron.presentation.screens.scheduler

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.domain.model.m3.M3Task
import ru.cityron.domain.utils.toInt
import ru.cityron.domain.utils.toTime
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.Switch

@Composable
fun SchedulersScreen(
    onClick: () -> Unit,
    onTaskClick: (Int) -> Unit,
    viewModel: SchedulersViewModel = hiltViewModel()
) {
    val state by viewModel.state().collectAsState()
    BackScaffold(
        title = "Планировщик",
        onClick = onClick,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(state.tasks) { task ->
                ScheduleCard(
                    task = task,
                    onCheckedChange = { viewModel.intent(SchedulersViewIntent.OnCheckedChange(task.i, it.toInt())) },
                    onClick = { onTaskClick(task.i) }
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
            .clip(RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colors.primary)
            .clickable { onClick() }
            .padding(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "${task.temp}°с",
                    fontSize = 32.sp
                )
                Text(
                    text = time,
                    fontSize = 32.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.fan),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "${task.fan}",
                        fontSize = 32.sp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
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
        )
    }
}

@Composable
fun VDivider() {
    Divider(
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
    )
}