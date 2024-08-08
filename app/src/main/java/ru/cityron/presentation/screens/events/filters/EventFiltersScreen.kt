package ru.cityron.presentation.screens.events.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.BottomSaveButton

@Composable
fun EventFiltersScreen(
    onClick: () -> Unit,
    viewModel: EventFiltersViewModel = hiltViewModel()
) {
    val state by viewModel.state().collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    BackScaffold(
        title = "Фильтры",
        onClick = onClick,
        bottomBar = {
            if (state.isChanged) {
                BottomSaveButton {
                    viewModel.intent(EventFiltersViewIntent.OnSaveClick)
                }
            }
        }
    ) {
        val menuItems = mapOf(
            "Журнал\nконтроллера" to Triple(
                state.sources,
                stringArrayResource(id = R.array.sources)
            ) { it: Int -> viewModel.intent(EventFiltersViewIntent.OnSourcesChange(it)) },
            "Все типы" to Triple(
                state.types,
                stringArrayResource(id = R.array.types),
            ) { it: Int -> viewModel.intent(EventFiltersViewIntent.OnTypesChange(it)) },
            "Все причины" to Triple(
                state.reasons,
                stringArrayResource(id = R.array.reasons),
            ) { it: Int -> viewModel.intent(EventFiltersViewIntent.OnReasonsChange(it)) },
            "Последние ..." to Triple(
                state.count,
                stringArrayResource(id = R.array.count),
            ) { it: Int -> viewModel.intent(EventFiltersViewIntent.OnCountChange(it)) },
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
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(24.dp)
                    .selectableGroup(),
            ) {
                val (value, values, onUpdate) = menuItems[menuItems.keys.toList()[selectedTab]]!!
                values.mapIndexed { i, it ->
                    val isSelected = value == i
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onUpdate(i) }
                    ) {
                        RadioButton(
                            modifier = Modifier,
                            selected = isSelected,
                            onClick = { onUpdate(i) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colors.primaryVariant,
                                unselectedColor = MaterialTheme.colors.secondaryVariant
                            )
                        )
                        Text(
                            text = it,
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.intent(EventFiltersViewIntent.Launch)
    }
}
