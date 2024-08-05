package ru.cityron.presentation.screens.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.domain.utils.Time
import ru.cityron.domain.utils.utilsBitGet
import ru.cityron.presentation.components.BackScaffoldWithState
import ru.cityron.ui.theme.LightGrey

@Composable
fun ControllerSettingsScreen(
    onClick: () -> Unit,
    onDatetimeClick: () -> Unit,
    onEthClick: () -> Unit,
    onWebClick: () -> Unit,
    onMetricClick: () -> Unit,
    viewModel: ControllerSettingsViewModel = hiltViewModel()
) {
    BackScaffoldWithState(
        title = "Контроллер",
        onClick = onClick,
        state = viewModel.state.collectAsState()
    ) { state ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                DatetimeItem(
                    rtcTime = state.rtcTime,
                    zone = state.zone,
                    timeSntp = state.timeSntp,
                    onClick = onDatetimeClick
                )
            }
            item {
                EthItem(
                    ipLoc = state.ipLoc,
                    dhcp = state.ethDhcp,
                    onClick = onEthClick
                )
            }
            item {
                WebInterfaceItem(
                    httpP1 = state.httpP1,
                    httpP2 = state.httpP2,
                    onClick = onWebClick
                )
            }
            item {
                MetricItem(
                    values = state.metricVal,
                    frequency = state.metricFrequency,
                    onClick = onMetricClick
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.intent(ControllerSettingsViewIntent.Launch)
    }
}

@Composable
fun DatetimeItem(
    rtcTime: Long,
    zone: Int,
    timeSntp: Int,
    onClick: () -> Unit
) {
    val split = Time.secondsToString(rtcTime, zone, Time.formatDatetime).split(" ")
    SettingItem(title = "Дата / Время", onClick = onClick) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Текущие дата и время",
                color = LightGrey,
                style = MaterialTheme.typography.h4
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = split[0],
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = split[1],
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h5
                )
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Настройка",
                color = LightGrey,
                style = MaterialTheme.typography.h4
            )
            Text(
                text = when (timeSntp) {
                    0 -> "Ручная"
                    else -> "Автоматическая"
                },
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Composable
fun EthItem(
    ipLoc: String,
    dhcp: Int,
    onClick: () -> Unit
) {
    SettingItem(title = "Сетевой интерфейс Ethernet", onClick = onClick) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Локальный IP-адрес",
                color = LightGrey,
                style = MaterialTheme.typography.h4
            )
            Text(
                text = ipLoc,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Настройка",
                color = LightGrey,
                style = MaterialTheme.typography.h4
            )
            Text(
                text = when (dhcp) {
                    0 -> "Ручная"
                    else -> "Автоматическая (DHCP)"
                },
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Composable
fun WebInterfaceItem(
    httpP1: String,
    httpP2: String,
    onClick: () -> Unit
) {
    SettingItem(title = "Доступ к веб-интерфейсу", onClick = onClick) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Пароль на чтение",
                color = LightGrey,
                style = MaterialTheme.typography.h4
            )
            Text(
                text = httpP1,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Пароль на изменение",
                color = LightGrey,
                style = MaterialTheme.typography.h4
            )
            Text(
                text = httpP2,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Composable
fun MetricItem(
    values: Int,
    frequency: Int,
    onClick: () -> Unit
) {
    val mValues = stringArrayResource(id = R.array.metric_values)
        .filterIndexed { i, _ -> utilsBitGet(values, i)  }
        .joinToString()
    val frequencies = stringArrayResource(id = R.array.metric_frequency)

    val index = when (frequency) {
        1 -> 0
        3 -> 1
        6 -> 2
        6 * 5 -> 3
        6 * 10 -> 4
        6 * 30 -> 5
        else -> 0
    }

    SettingItem(title = "Журнал метрик контроллера", onClick = onClick) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Значение температур",
                color = LightGrey,
                style = MaterialTheme.typography.h4
            )
            Text(
                text = mValues,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Частота сохранения",
                color = LightGrey,
                style = MaterialTheme.typography.h4
            )
            Text(
                text = frequencies[index],
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary, RoundedCornerShape(4.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
            )
            Icon(
                painter = painterResource(id = R.drawable.setting_3),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClick() },
                tint = MaterialTheme.colors.primaryVariant
            )
        }
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = content
        )
    }
}