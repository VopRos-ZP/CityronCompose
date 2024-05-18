package ru.cityron.presentation.screens.m3tabs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RangeSlider
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.R
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.DrawerScaffold
import ru.cityron.presentation.components.Thermostat
import ru.cityron.presentation.screens.events.EventsScreen
import ru.cityron.presentation.screens.metrics.MetricsScreen

@Composable
fun M3TabsScreen(
    onClick: () -> Unit,
    viewModel: M3ViewModel = hiltViewModel()
) {
    val pair by viewModel.controller.collectAsState()
    DrawerScaffold(title = pair?.first?.name ?: "", onClick = onClick) {
        M3TabsScreenContent()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun M3TabsScreenContent() {
    val pages = listOf(
        TabWithPage("Уставка", R.drawable.temp) { M3TempScreen() },
        TabWithPage("События", R.drawable.task) { EventsScreen() },
        TabWithPage("Метрики", R.drawable.metrics) { MetricsScreen() },
    )
    val pagerState = rememberPagerState { pages.size }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            pages.forEachIndexed { i, tab ->
                Tab(
                    icon = {
                        Icon(
                            painter = painterResource(id = tab.icon),
                            contentDescription = null
                        )
                    },
                    text = { Text(text = tab.title, fontSize = 12.sp) },
                    selected = pagerState.currentPage == i,
                    onClick = { scope.launch { pagerState.animateScrollToPage(i) } }
                )
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
    var fan by remember { mutableFloatStateOf(3f) }

    Column {
        Thermostat(224)
        Column {
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = fan,
                onValueChange = { fan = it },
                steps = 3,
                valueRange = 1f..5f,
                colors = SliderDefaults.colors(
                    inactiveTickColor = MaterialTheme.colors.primary,
                    activeTickColor = MaterialTheme.colors.primary,
                    thumbColor = MaterialTheme.colors.primaryVariant,
                    activeTrackColor = MaterialTheme.colors.primaryVariant,
                    inactiveTrackColor = MaterialTheme.colors.primaryVariant,
                ),
                onValueChangeFinished = {

                }
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
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchState()
    }
}

data class TabWithPage(
    val title: String,
    val icon: Int,
    val screen: @Composable () -> Unit
)

@Composable
fun CustomSlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    trackColor: Color = Color.Gray,
    thumbColor: Color = Color.Blue,
    trackWidth: Float = 10f,
    thumbRadius: Float = 14f
) {
    var sliderWidth by remember { mutableFloatStateOf(0f) }
    var sliderPosition by remember(value) { mutableFloatStateOf(value) }

    Box(
        modifier = modifier.width(250.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val newValue = (offset.x / sliderWidth).coerceIn(0f, 1f)
                    onValueChange(newValue)
                }
            }
            .onSizeChanged {
                sliderWidth = it.width.toFloat()
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw the slider track
            val trackStart = Offset(0f, size.height / 2)
            val trackEnd = Offset(size.width, size.height / 2)
            drawLine(
                color = trackColor,
                start = trackStart,
                end = trackEnd,
                strokeWidth = trackWidth
            )

            // Draw the thumb
            val thumbX = sliderPosition * size.width
            val thumbCenter = Offset(thumbX, size.height / 2)
            drawCircle(
                color = thumbColor,
                radius = thumbRadius,
                center = thumbCenter
            )
        }
    }
}