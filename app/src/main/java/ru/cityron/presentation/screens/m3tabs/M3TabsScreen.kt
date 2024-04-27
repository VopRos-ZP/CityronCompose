package ru.cityron.presentation.screens.m3tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.presentation.screens.events.EventsScreen
import ru.cityron.presentation.screens.metrics.MetricsScreen

@Composable
fun M3TabsScreen() {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "") })
        }
    ) {
        M3TabsScreenContent(it)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun M3TabsScreenContent(
    paddingValues: PaddingValues
) {
    val pages = listOf(
        TabWithPage("Температура") { M3TempScreen() },
        TabWithPage("События") { EventsScreen() },
        TabWithPage("Метрики") { MetricsScreen() },
    )
    val pagerState = rememberPagerState { pages.size }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            pages.forEachIndexed { i, tab ->
                Tab(
                    selected = pagerState.currentPage == i,
                    onClick = { scope.launch { pagerState.animateScrollToPage(i) } }
                ) {
                    Text(text = tab.title)
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) {
            pages[it].screen()
        }
    }
}

@Composable
private fun M3TempScreen() {
    val viewModel: M3ViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()


    LaunchedEffect(key1 = Unit) {
        viewModel.fetchState()
    }
}

data class TabWithPage(
    val title: String,
    val screen: @Composable () -> Unit
)