package ru.cityron.presentation.screens.alarms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.Switch

@Composable
fun AlarmsScreen(
    onClick: () -> Unit,
    onEditAlarmClick: (Int) -> Unit,
    viewModel: AlarmsViewModel = hiltViewModel()
) {
    val alarmsStrings = stringArrayResource(id = R.array.alarms_m3)
    val state by viewModel.state().collectAsState()
    BackScaffold(
        title = "Аварии",
        onClick = onClick
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(state.alarms) { alarm ->
                AlarmScreenItem(
                    text = alarmsStrings[alarm.i - 1],
                    isChecked = alarm.en == 1,
                    onCheckedChange = { viewModel.intent(AlarmsViewIntent.OnAlarmEnChange(alarm, it)) },
                    onClick = { onEditAlarmClick(alarm.i) }
                )
            }
        }
    }
}

@Composable
fun AlarmScreenItem(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.primary)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h4.copy(letterSpacing = 0.sp)
        )
        Spacer(modifier = Modifier.width(13.dp))
        Switch(
            modifier = Modifier.height(30.dp),
            checked = isChecked,
            onCheckedChange = onCheckedChange,
        )
    }
}