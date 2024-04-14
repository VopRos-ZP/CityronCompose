package com.vopros.cityron.m3.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.highsoft.highcharts.common.hichartsclasses.HIChart
import com.highsoft.highcharts.common.hichartsclasses.HIOptions
import com.highsoft.highcharts.common.hichartsclasses.HISeries
import com.highsoft.highcharts.common.hichartsclasses.HITitle
import com.highsoft.highcharts.core.HIChartView
import com.ramcosta.composedestinations.annotation.Destination
import com.vopros.cityron.ui.components.ListItemPicker
import com.vopros.cityron.ui.components.Loading
import com.vopros.cityron.ui.components.tab.ControllerPagerTab
import com.vopros.cityron.ui.navigation.ControllerNavGraph
import com.vopros.cityron.ui.screens.Screen
import com.vopros.cityron.ui.screens.events.EventsViewModel
import com.vopros.cityron.utils.Temp
import kotlinx.coroutines.launch
import ru.cityron.core.domain.model.Event
import java.util.ArrayList

@OptIn(ExperimentalFoundationApi::class)
@ControllerNavGraph
@Destination
@Composable
fun M3Screen() {
    val viewModel: M3ViewModel = hiltViewModel()
    val state = viewModel.state

    val scope = rememberCoroutineScope()
    var index by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { 3 }
    val scrollToPage = { i: Int ->
        index = i
        scope.launch { pagerState.animateScrollToPage(index) }
    }
    Screen(title = "") {
        if (state.message != null) {
            Text(text = "Связь потерена", color = Color.Red)
        }
        if (state.isLoading) {
            Loading()
        }
        if (state.all != null) {
            val tabs = listOf(
                ControllerPagerTab(
                    title = "Температура",
                    content = {
                        FirstPage(state.all.state.set.temp) {
                            viewModel.conf("set-temp", it)
                        }
                    }
                ),
                ControllerPagerTab(
                    title = "События",
                    content = { SecondPage() }
                ),
                ControllerPagerTab(
                    title = "Метрики",
                    content = { ThirdPage() }
                ),
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                TabRow(
                    selectedTabIndex = index,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tabs.mapIndexed { i, tab ->
                        Tab(
                            selected = index == i,
                            onClick = { scrollToPage(i) },
                            text = { Text(text = tab.title, fontSize = 10.sp) }
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false
                ) {
                    tabs[it].content()
                }
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchAll()
    }
}

@Composable
fun FirstPage(temp: Int, onChange: (Int) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ListItemPicker(
            modifier = Modifier.scale(2f),
            value = Temp.toGrade(temp),
            onValueChange = { s ->
                val value = s.replace(".", "").toInt()
                onChange(value)
            },
            list = (50..450).step(5).map { Temp.toGrade(it) }.toList()
        )
    }
}

/** Логи **/
@Composable
fun SecondPage() {
    val viewModel: EventsViewModel = hiltViewModel()
    val state = viewModel.state
    Box {
        when {
            state.isLoading -> Loading()
            state.events.isEmpty() -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Нет данных")
            }
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                state.events.map { (key, value) ->
                    item {
                        Column {
                            Text(text = key, fontSize = 12.sp)
                            value.map { uc -> Text(text = parseEvents(uc), fontSize = 10.sp) }
                        }
                    }
                }
            }
        }
    }
    DisposableEffect(Unit) {
        viewModel.fetchEvents(50, -1, -1, -1)
        onDispose {  }
    }
}

private fun parseEvents(event: Event) : AnnotatedString = buildAnnotatedString {
    append(event.date)
    when (event.type) {
        "Авария" -> withStyle(SpanStyle(color = Color.Red)) {
            append(" [${event.type}]")
        }
        else -> append(" [${event.type}]")
    }
    append(" ${event.result}")
}

@Composable
fun ThirdPage() {
    AndroidView(factory = {
        val view = HIChartView(it)

        val options = HIOptions()

        val chart = HIChart()
        chart.type = "column"

        val title = HITitle()
        title.text = "Demo chart"

        options.chart = chart
        options.title = title

        val series = HISeries()

        series.data = ArrayList(
            listOf(49.9, 71.5, 106.4, 129.2, 144, 176, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4)
        )
        options.series = ArrayList(listOf(series))

        view.options = options

        view
    }, modifier = Modifier.fillMaxSize())
}
