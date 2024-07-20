package ru.cityron.presentation.screens.events.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.screens.events.EventsScreenViewModel

@Composable
fun EventFiltersScreen(
    viewModel: EventsScreenViewModel = hiltViewModel(),
    onClick: () -> Unit
) {
    BackScaffold(title = "Фильтры", onClick = onClick) {
        LeftMenu(viewModel = viewModel)
    }
}

@Composable
fun LeftMenu(
    viewModel: EventsScreenViewModel
) {
    val count by viewModel.count.collectAsState()
    val types by viewModel.types.collectAsState()
    val sources by viewModel.sources.collectAsState()
    val reasons by viewModel.reasons.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    val menuItems = mapOf(
        "Журнал\nконтроллера" to Triple(
            sources,
            stringArrayResource(id = R.array.sources),
            viewModel::setSources
        ),
        "Все типы" to Triple(
            types,
            stringArrayResource(id = R.array.types),
            viewModel::setTypes
        ),
        "Все причины" to Triple(
            reasons,
            stringArrayResource(id = R.array.reasons),
            viewModel::setReasons
        ),
        "Последние ..." to Triple(
            count,
            stringArrayResource(id = R.array.count),
            viewModel::setCount
        ),
    )

    Row(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.35f)
                .fillMaxHeight()
                .selectableGroup()
                .background(MaterialTheme.colors.primary)
        ) {
            menuItems.keys.mapIndexed { i, it ->
                val isSelected = selectedTab == i
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isSelected) MaterialTheme.colors.background else MaterialTheme.colors.primary)
                        .selectable(
                            selected = isSelected,
                            onClick = { selectedTab = i }
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 12.sp
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp)
                .selectableGroup()
        ) {
            val (value, values, onClick) = menuItems[menuItems.keys.toList()[selectedTab]]!!
            values.mapIndexed { i, it ->
                val isSelected = value == i
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { onClick(i) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colors.primaryVariant,
                            unselectedColor = MaterialTheme.colors.secondaryVariant
                        )
                    )
                    Text(
                        text = it,
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
