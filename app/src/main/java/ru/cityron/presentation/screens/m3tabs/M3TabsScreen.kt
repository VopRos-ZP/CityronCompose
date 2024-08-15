package ru.cityron.presentation.screens.m3tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.presentation.components.DrawerScaffold
import ru.cityron.presentation.components.HTabRow
import ru.cityron.presentation.components.TabWithPage
import ru.cityron.presentation.screens.events.EventsScreen
import ru.cityron.presentation.screens.m3temp.M3TempScreen
import ru.cityron.presentation.screens.metrics.MetricsScreen

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
        title = pair?.name ?: "",
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
