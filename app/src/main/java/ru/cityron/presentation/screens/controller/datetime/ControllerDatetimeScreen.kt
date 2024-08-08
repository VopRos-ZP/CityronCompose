package ru.cityron.presentation.screens.controller.datetime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.domain.utils.toTimeZone
import ru.cityron.presentation.components.AlgoBooleanItem
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.components.DropDownMenu
import ru.cityron.presentation.components.TextFieldItem
import ru.cityron.presentation.components.rememberSnackbarResult

@Composable
fun ControllerDatetimeScreen(
    onClick: () -> Unit,
    viewModel: ControllerDatetimeViewModel = hiltViewModel()
) {
    val state by viewModel.state().collectAsState()
    val viewAction = viewModel.action().collectAsState(initial = null)
    var snackbarResult by rememberSnackbarResult()
    BackScaffold(
        title = "Дата / Время",
        onClick = onClick,
        snackbarResult = snackbarResult,
        onDismissSnackbar = { if (!isError) viewModel.intent(ControllerDatetimeViewIntent.OnSnackbarDismiss) },
        bottomBar = {
            if (state.isChanged) {
                BottomSaveButton(enabled = state.timeIpIsCorrect && state.dateIsCorrect && state.timeIsCorrect) {
                    viewModel.intent(ControllerDatetimeViewIntent.OnSaveClick)
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
            val enabled = state.timeFSntp != 1
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
                    .padding(horizontal = 16.dp, vertical = 20.dp),
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
                        onValueChange = {
                            if (it != null) viewModel.intent(ControllerDatetimeViewIntent.OnTimeIpChange(it))
                        },
                        transform = { it },
                        keyboardType = KeyboardType.Number,
                        placeholder = state.timeIpOld,
                        enabled = enabled,
                        isError = !state.timeIpIsCorrect
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
                        onValueChange = {
                            if (it != null) viewModel.intent(ControllerDatetimeViewIntent.OnDateChange(it))
                        },
                        transform = { it },
                        keyboardType = KeyboardType.Number,
                        placeholder = state.dateOld,
                        enabled = enabled,
                        isError = !state.dateIsCorrect
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
                        onValueChange = {
                            if (it != null) viewModel.intent(ControllerDatetimeViewIntent.OnTimeChange(it))
                        },
                        transform = { it },
                        keyboardType = KeyboardType.Text,
                        placeholder = state.timeOld,
                        enabled = enabled,
                        isError = !state.timeIsCorrect
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
                    DropDownMenu(
                        modifier = Modifier.weight(1f),
                        value = state.timeZone,
                        onValueChange = {
                            viewModel.intent(ControllerDatetimeViewIntent.OnTimeZoneChange(it))
                        },
                        format = { "GMT${it.toTimeZone()}" },
                        items = state.timeZoneRange.toList()
                    )
                }
            }
        }
    }
    LaunchedEffect(viewAction.value) {
        when (val action = viewAction.value) {
            is ControllerDatetimeViewAction.ShowSnackbar -> snackbarResult = action.result
            null -> viewModel.intent(ControllerDatetimeViewIntent.Launch)
        }
    }
}
