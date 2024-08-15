package ru.cityron.presentation.screens.controller.metric

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.domain.utils.utilsBitGet
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.components.rememberSnackbarResult
import ru.cityron.presentation.screens.editScheduler.DayChip
import ru.cityron.presentation.screens.editScheduler.TitledContent
import kotlin.math.pow

@Composable
fun ControllerMetricScreen(
    onClick: () -> Unit,
    viewModel: ControllerMetricViewModel = hiltViewModel()
) {
    val valuesStrings = stringArrayResource(id = R.array.metric_values)
    val frequencyStrings = stringArrayResource(id = R.array.metric_frequency_short)

    val state by viewModel.state().collectAsState()
    val viewAction = viewModel.action().collectAsState(initial = null)
    var snackbarResult by rememberSnackbarResult()
    BackScaffold(
        title = "Журнал метрик",
        onClick = onClick,
        snackbarResult = snackbarResult,
        onDismissSnackbar = { if (!isError) viewModel.intent(ControllerMetricViewIntent.OnSnackbarDismiss) },
        bottomBar = {
            if (state.isChanged) {
                BottomSaveButton {
                    viewModel.intent(ControllerMetricViewIntent.OnSaveClick)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Сохранять в памяти контроллера температуру с датчиков",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center
            )
            TitledContent(
                title = "Место",
                paddingValues = PaddingValues(0.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    valuesStrings.mapIndexed { i, s ->
                        val selected = utilsBitGet(state.valuesBits, i)
                        DayChip(
                            modifier = Modifier.width(112.dp),
                            day = s,
                            isSelected = selected,
                            onClick = {
                                val value = 2.0.pow(i).toInt()
                                val func: (Int, Int) -> Int =
                                    if (selected) { a, b -> a - b } else { a, b -> a + b }
                                viewModel.intent(
                                    ControllerMetricViewIntent.OnValuesBitsChange(
                                        func(
                                            state.valuesBits,
                                            value
                                        )
                                    )
                                )
                            }
                        )
                    }
                }
            }
            TitledContent(
                title = "Частота сохранения: каждые ...",
                paddingValues = PaddingValues(0.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    (0..<frequencyStrings.size / 2).map { i ->
                        DayChip(
                            modifier = Modifier.width(112.dp),
                            day = frequencyStrings[i],
                            isSelected = state.frequency == i,
                            onClick = {
                                viewModel.intent(ControllerMetricViewIntent.OnFrequencyChange(i))
                            }
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    (frequencyStrings.size / 2..<frequencyStrings.size).map { i ->
                        DayChip(
                            modifier = Modifier.width(112.dp),
                            day = frequencyStrings[i],
                            isSelected = state.frequency == i,
                            onClick = {
                                viewModel.intent(ControllerMetricViewIntent.OnFrequencyChange(i))
                            }
                        )
                    }
                }
            }
            TitledContent(
                title = "Емкость журнала",
                paddingValues = PaddingValues(0.dp),
                trailing = {
                    Text(
                        text = "${state.capacity}",
                        style = MaterialTheme.typography.h3.copy(fontSize = 18.sp)
                    )
                },
                content = {}
            )
        }
    }
    LaunchedEffect(viewAction.value) {
        when (val action = viewAction.value) {
            is ControllerMetricViewAction.ShowSnackbar -> snackbarResult = action.result
            null -> viewModel.intent(ControllerMetricViewIntent.Launch)
        }
    }
}