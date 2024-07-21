package ru.cityron.presentation.screens.alarms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.presentation.components.BackScaffold

@Composable
fun AlarmsScreen(
    onClick: () -> Unit,
    viewModel: AlarmsViewModel = hiltViewModel()
) {
    val alarms by viewModel.alarms.collectAsState()
    BackScaffold(
        title = "Аварии",
        onClick = onClick
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchAlarms()
    }
}

@Composable
fun AlarmScreenItem(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row {

    }
}