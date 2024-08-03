package ru.cityron.presentation.screens.controller.datetime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.presentation.components.AlgoBooleanItem
import ru.cityron.presentation.components.BackScaffoldWithState
import ru.cityron.presentation.components.OutlinedTextFieldItem
import ru.cityron.presentation.components.TextFieldItem

@Composable
fun ControllerDatetimeScreen(
    onClick: () -> Unit,
    viewModel: ControllerDatetimeViewModel = hiltViewModel()
) {
    val stateState = viewModel.state.collectAsState()
    BackScaffoldWithState(
        title = "Дата / Время",
        onClick = onClick,
        state = stateState
    ) { state ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            AlgoBooleanItem(
                text = "Получать автоматически",
                value = state.timeFSntp,
                onValueChange = { viewModel.intent(ControllerDatetimeViewIntent.OnTimeFSntpChange(it)) }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colors.primary)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    ,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Сервер времени (SNTP)",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                    TextFieldItem(
                        modifier = Modifier.weight(1f),
                        value = state.timeIp,
                        onValueChange = { viewModel.intent(ControllerDatetimeViewIntent.OnTimeIpChange(it)) },
                        transform = { it },
                        keyboardType = KeyboardType.Number,
                        placeholder = state.timeIpOld
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Дата",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                    TextFieldItem(
                        modifier = Modifier.weight(1f),
                        value = state.date,
                        onValueChange = { viewModel.intent(ControllerDatetimeViewIntent.OnTimeIpChange(it)) },
                        transform = { it },
                        keyboardType = KeyboardType.Number,
                        placeholder = state.dateOld
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Время",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                    TextFieldItem(
                        modifier = Modifier.weight(1f),
                        value = state.time,
                        onValueChange = { viewModel.intent(ControllerDatetimeViewIntent.OnTimeIpChange(it)) },
                        transform = { it },
                        keyboardType = KeyboardType.Text,
                        placeholder = state.timeOld
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Часовой пояс",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                    TextFieldItem(
                        modifier = Modifier.weight(1f),
                        value = state.timeIp,
                        onValueChange = { viewModel.intent(ControllerDatetimeViewIntent.OnTimeIpChange(it)) },
                        transform = { it },
                        keyboardType = KeyboardType.Text,
                        placeholder = state.timeIpOld
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.intent(ControllerDatetimeViewIntent.Launch)
    }
}
