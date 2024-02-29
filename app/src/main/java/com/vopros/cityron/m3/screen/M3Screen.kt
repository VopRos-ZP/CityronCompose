package com.vopros.cityron.m3.screen

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
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.vopros.cityron.controller.ControllerItem
import com.vopros.cityron.m3.domain.M3State
import com.vopros.cityron.navigation.MainNavGraph
import com.vopros.cityron.ui.components.ListItemPicker
import com.vopros.cityron.ui.components.Loading
import com.vopros.cityron.ui.components.tab.ControllerPagerTab
import com.vopros.cityron.ui.screens.Screen
import com.vopros.cityron.ui.screens.controller.EventUseCase
import com.vopros.cityron.utils.Temp
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.time.format.DateTimeFormatter

@MainNavGraph
@Destination
@Composable
fun M3Screen(
    controllerItemString: String = "",
    viewModel: M3ControllerViewModel = hiltViewModel(),
) {
    val item = Json.decodeFromString<ControllerItem>(controllerItemString)
    val scope = rememberCoroutineScope()
    val stateState = viewModel.state.collectAsState()
    var index by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { 3 }
    val scrollToPage = { i: Int ->
        index = i
        scope.launch { pagerState.animateScrollToPage(index) }
    }
    Screen(title = item.name) {
        when (val state = stateState.value) {
            null -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Связь потерена", color = Color.Red)
            }
            else -> {
                val tabs = listOf(
                    ControllerPagerTab(
                        title = "Температура",
                        content = { FirstPage(item, state, viewModel) }
                    ),
                    ControllerPagerTab(
                        title = "События",
                        content = { SecondPage(item, viewModel) }
                    ),
                    ControllerPagerTab(
                        title = "Планировщик",
                        content = { /*ThirdPage(item, state, viewModel)*/ }
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
    }
}

@Composable
fun FirstPage(
    controllerItem: ControllerItem,
    m3: M3State,
    viewModel: M3ControllerViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ListItemPicker(
            modifier = Modifier.scale(2f),
            value = Temp.toGrade(m3.state.set.temp),
            onValueChange = { s ->
                val value = s.replace(".", "").toInt()
                viewModel.updateState(controllerItem, "set-temp", value)
            },
            list = (50..450).step(5).map { Temp.toGrade(it) }.toList()
        )
    }
    DisposableEffect(Unit) {
        viewModel.fetchState(controllerItem)
        onDispose { viewModel.disposeState() }
    }
}

/** Логи **/
@Composable
fun SecondPage(
    controllerItem: ControllerItem,
    viewModel: M3ControllerViewModel
) {
    val eventsState = viewModel.events.collectAsState()
    Column {
        when (val events = eventsState.value) {
            null -> Loading()
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                events.map { (key, value) ->
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
        viewModel.fetchLog(controllerItem, 50, -1, -1, -1)
        onDispose { viewModel.disposeState() }
    }
}

private fun parseEvents(useCase: EventUseCase) : AnnotatedString = buildAnnotatedString {
    val format = DateTimeFormatter.ofPattern("HH:mm:ss")

    append(useCase.date.toLocalTime().format(format))
    val style = when (useCase.type) {
        "Авария" -> Color.Red
        else -> Color.Black
    }
    withStyle(SpanStyle(color = style)) {
        append(" [${useCase.type}]")
    }
    append(" ${useCase.result}")
}