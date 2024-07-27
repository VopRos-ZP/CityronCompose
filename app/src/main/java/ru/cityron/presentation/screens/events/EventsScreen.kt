package ru.cityron.presentation.screens.events

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import ru.cityron.R
import ru.cityron.domain.model.Event
import ru.cityron.domain.model.EventWithDate
import ru.cityron.presentation.components.Loader

@Composable
fun EventsScreen(
    viewModel: EventsScreenViewModel = hiltViewModel(),
    onFilterClick: () -> Unit,
) {
    val isFiltered by viewModel.isFiltered.collectAsState()
    val eventsState = viewModel.events.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterButton(
            isFiltered = isFiltered,
            onClick = onFilterClick
        )
        when (val events = eventsState.value) {
            null -> Loader()
            emptyList<EventWithDate>() -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(text = "Событий нет")
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(events) {
                        DateWithEventListItem(eventWithDate = it)
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchEventsFromStore()
        delay(1000)
        viewModel.fetchEvents()
    }
}

@Composable
fun FilterButton(
    isFiltered: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            if (isFiltered) {
                Box(
                    modifier = Modifier
                        .offset(x = 5.dp, y = (-5).dp)
                        .size(8.dp)
                        .clip(RoundedCornerShape(percent = 100))
                        .background(MaterialTheme.colors.secondary)

                )
            }
        }
        Text(
            text = "Фильтры",
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
fun DateWithEventListItem(eventWithDate: EventWithDate) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = eventWithDate.day,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h5
        )
        eventWithDate.event.map {
            EventTextItem(it)
        }
    }
}

@Composable
fun EventTextItem(event: Event) {
    val (icon, color) = when (event.type) {
        "Авария" -> R.drawable.danger to MaterialTheme.colors.error
        "Конфигуарция" -> R.drawable.config to MaterialTheme.colors.secondary
        "Сервис" -> R.drawable.setting_2 to MaterialTheme.colors.secondary
        "Работа" -> R.drawable.tick_circle to MaterialTheme.colors.secondary
        else -> throw RuntimeException("Type ${event.type} not found")
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = event.type,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = "${event.time} ${event.result}",
            style = MaterialTheme.typography.body1
        )
    }
}