package ru.cityron.presentation.screens.events

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.presentation.components.DrawerScaffold

@Composable
fun EventsScreen(
    viewModel: EventsScreenViewModel = hiltViewModel(),
    onFilterClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.End
    ) {
        FilterButton(onClick = onFilterClick)
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

        }
    }
}

@Composable
fun FilterButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.filter),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(text = "Фильтры")
    }
}