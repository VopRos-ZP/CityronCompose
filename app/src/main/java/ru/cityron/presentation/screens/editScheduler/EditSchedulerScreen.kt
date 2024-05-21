package ru.cityron.presentation.screens.editScheduler

import android.inputmethodservice.Keyboard.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.domain.utils.toTime
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.Picker
import ru.cityron.presentation.components.rememberPickerState

@Composable
fun EditSchedulerScreen(
    onClick: () -> Unit,
    viewModel: EditSchedulerViewModel = hiltViewModel()
) {
    val task by viewModel.localTask.collectAsState()

    BackScaffold(
        title = "Планировщик",
        onClick = onClick
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                TitledContent(title = "Время") {
                    TimePicker(
                        hour = task.hour,
                        onHourChanged = {},
                        min = task.min,
                        onMinChanged = {}
                    )
                }
            }
            item {
                TitledContent(title = "Дата") {

                }
            }
        }
    }
}

@Composable
fun TitledContent(
    title: String,
    trailing: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            if (trailing != null) {
                trailing()
            }
        }
        content()
    }
}

@Composable
fun TimePicker(
    hour: Int,
    onHourChanged: (Int) -> Unit,
    min: Int,
    onMinChanged: (Int) -> Unit
) {
    val hourValues = (0..23).map(::toTime)
    val minValues = (0..59).map(::toTime)

    val hourPickerState = rememberPickerState()
    val minPickerState = rememberPickerState()
    Row(modifier = Modifier.fillMaxWidth(0.5f)) {
        Picker(
            state = hourPickerState,
            items = hourValues,
            visibleItemsCount = 5,
            startIndex = hourValues.indexOf(toTime(hour)),
            textModifier = Modifier.padding(8.dp),
            modifier = Modifier.fillMaxWidth(0.5f),
            textStyle = TextStyle(fontSize = 32.sp),
            dividerColor = MaterialTheme.colors.onBackground
        )
        Picker(
            state = minPickerState,
            items = minValues,
            visibleItemsCount = 5,
            startIndex = minValues.indexOf(toTime(min)),
            textModifier = Modifier.padding(8.dp),
            textStyle = TextStyle(fontSize = 32.sp),
            dividerColor = MaterialTheme.colors.onBackground
        )
    }
    LaunchedEffect(key1 = hourPickerState.selectedItem) {
        onHourChanged(hourPickerState.selectedItem.toInt())
    }
    LaunchedEffect(key1 = minPickerState.selectedItem) {
        onMinChanged(minPickerState.selectedItem.toInt())
    }
}