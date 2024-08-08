package ru.cityron.presentation.screens.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.presentation.components.BackScaffold

@Composable
fun AlertsScreen(
    onClick: () -> Unit,
    viewModel: AlertsViewModel = hiltViewModel()
) {
    val titles = stringArrayResource(id = R.array.alarms_m3)
    val state by viewModel.state().collectAsState()
    BackScaffold(title = "Аварии", onClick = onClick) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(state.alerts) { ErrorItem(text = it) }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.intent(AlertsViewIntent.OnAlertsArrayChange(titles.toList()))
    }
}

@Composable
fun ErrorItem(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.error,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 16.dp, vertical = 20.dp),
        text = text,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.h4
    )
}