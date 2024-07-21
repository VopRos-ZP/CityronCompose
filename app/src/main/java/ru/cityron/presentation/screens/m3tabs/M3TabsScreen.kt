package ru.cityron.presentation.screens.m3tabs

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.domain.utils.Temp
import ru.cityron.presentation.components.DrawerScaffold
import ru.cityron.presentation.components.FanSlider
import ru.cityron.presentation.components.HTabRow
import ru.cityron.presentation.components.TabWithPage
import ru.cityron.presentation.components.Thermostat
import ru.cityron.presentation.screens.events.EventsScreen
import ru.cityron.presentation.screens.metrics.MetricsScreen
import ru.cityron.ui.theme.Green
import ru.cityron.ui.theme.Red

@Composable
fun M3TabsScreen(
    onClick: () -> Unit,
    onAlertsClick: () -> Unit,
    onSchedulerClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onFilterClick: () -> Unit,
    viewModel: M3ViewModel = hiltViewModel()
) {
    val pair by viewModel.controller.collectAsState()
    DrawerScaffold(
        title = pair?.first?.name ?: "",
        onClick = onClick,
        onSettingsClick = onSettingsClick
    ) {
        M3TabsScreenContent(
            onAlertsClick = onAlertsClick,
            onSchedulerClick = onSchedulerClick,
            onFilterClick = onFilterClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun M3TabsScreenContent(
    onAlertsClick: () -> Unit,
    onSchedulerClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    val pages = listOf(
        TabWithPage("Уставка", R.drawable.temp) { M3TempScreen(onAlertsClick, onSchedulerClick) },
        TabWithPage("События", R.drawable.task) { EventsScreen(onFilterClick = onFilterClick) },
        TabWithPage("Метрики", R.drawable.metrics) { MetricsScreen() },
    )
    val pagerState = rememberPagerState { pages.size }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HTabRow(pagerState = pagerState, tabs = pages)
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) {
            pages[it].screen()
        }
    }
}

@Composable
private fun M3TempScreen(
    onAlertsClick: () -> Unit,
    onSchedulerClick: () -> Unit,
) {
    val viewModel: M3ViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    if (state != null) {
        var fan by remember { mutableIntStateOf(state!!.set.fan) }
        val statusColor by remember(state) {
            mutableStateOf(
                when (state!!.set.power == 1) {
                    true -> Green
                    else -> Red
                }
            )
        }
        val statusOffset = (-5).dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(contentAlignment = Alignment.Center) {
                Thermostat(
                    modifier = Modifier.size(275.dp),
                    value = state!!.set.temp,
                    minValue = 50,
                    maxValue = 450,
                    step = 50,
                    onPositionChange = {
                        viewModel.setTemp(it)
                    }
                )
                Text(
                    text = Temp.toGrade(state!!.algo.tempPv),
                    color = MaterialTheme.colors.primaryVariant,
                    fontSize = 62.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.grade),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .offset(x = 75.dp, y = (-35).dp),
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FanSlider(
                    values = listOf(1, 2, 3, 4, 5),
                    value = state!!.set.fan,
                    onValueChange = { fan = it },
                    onFinishChange = { viewModel.setFan(it) }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "$fan",
                        color = MaterialTheme.colors.primaryVariant,
                        fontSize = 32.sp
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.fan),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .offset(statusOffset, statusOffset)
                            .background(
                                color = statusColor,
                                shape = RoundedCornerShape(percent = 100)
                            )
                    )
                    Button(
                        shape = RoundedCornerShape(percent = 100),
                        modifier = Modifier.size(90.dp),
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.on_off),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            tint = MaterialTheme.colors.primaryVariant
                        )
                    }
                }
                Button(
                    shape = RoundedCornerShape(percent = 100),
                    modifier = Modifier.size(90.dp),
                    onClick = onSchedulerClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = MaterialTheme.colors.primaryVariant
                    )
                }
            }
            AnimatedContent(targetState = state!!.alarms, label = "") {
                val isShow = it != 0
                val (bg, content) = when (isShow) {
                    true -> MaterialTheme.colors.error to MaterialTheme.colors.onBackground
                    else -> MaterialTheme.colors.background to Color.Transparent
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = onAlertsClick,
                    enabled = isShow,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = bg,
                        contentColor = content,
                        disabledContentColor = content,
                        disabledBackgroundColor = bg
                    ),
                    contentPadding = PaddingValues(20.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.danger),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = "Аварии")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchState()
    }
}
