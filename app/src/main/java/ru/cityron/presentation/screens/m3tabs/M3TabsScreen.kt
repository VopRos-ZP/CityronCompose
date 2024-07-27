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
import ru.cityron.presentation.screens.m3temp.M3TempScreen
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
            onFilterClick = onFilterClick,
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun M3TabsScreenContent(
    onAlertsClick: () -> Unit,
    onSchedulerClick: () -> Unit,
    onFilterClick: () -> Unit,
    viewModel: M3ViewModel,
) {
    val pages = listOf(
        TabWithPage("Уставка", R.drawable.temp) {
            M3TempScreen(
                onAlertsClick = onAlertsClick,
                onSchedulerClick = onSchedulerClick,
                viewModel = viewModel,
            )
        },
        TabWithPage("События", R.drawable.task) {
            EventsScreen(onFilterClick = onFilterClick)
        },
        TabWithPage("Метрики", R.drawable.metrics) {
            MetricsScreen()
        },
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
