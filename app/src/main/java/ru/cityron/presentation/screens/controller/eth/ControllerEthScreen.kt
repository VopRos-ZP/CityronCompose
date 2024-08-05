package ru.cityron.presentation.screens.controller.eth

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.presentation.components.AlgoBooleanItem
import ru.cityron.presentation.components.BackScaffoldWithState
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.components.TextFieldItem

@Composable
fun ControllerEthScreen(
    onClick: () -> Unit,
    viewModel: ControllerEthViewModel = hiltViewModel()
) {
    val stateState = viewModel.state.collectAsState()
    BackScaffoldWithState(
        title = "Сетевой интерфейс Eth",
        onClick = onClick,
        state = stateState,
        bottomBar = {
            if (stateState.value?.isChanged == true) {
                BottomSaveButton {
                    viewModel.intent(ControllerEthViewIntent.OnSaveClick)
                }
            }
        }
    ) { state ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            val enabled = state.fDhcp != 1
            AlgoBooleanItem(
                text = "Получать автоматически",
                value = state.fDhcp,
                onValueChange = { viewModel.intent(ControllerEthViewIntent.OnFDhcpChange(it)) }
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
                        text = "Локальный IP - адрес",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                    TextFieldItem(
                        modifier = Modifier.weight(1f),
                        value = state.ip,
                        onValueChange = {
                            viewModel.intent(ControllerEthViewIntent.OnIpChange(it))
                        },
                        transform = { it },
                        keyboardType = KeyboardType.Number,
                        placeholder = state.ipOld,
                        enabled = enabled
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Маска подсети",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                    TextFieldItem(
                        modifier = Modifier.weight(1f),
                        value = state.mask,
                        onValueChange = {
                            viewModel.intent(ControllerEthViewIntent.OnMaskChange(it))
                        },
                        transform = { it },
                        keyboardType = KeyboardType.Number,
                        placeholder = state.maskOld,
                        enabled = enabled
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "IP-адрес шлюза (роутера)",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                    TextFieldItem(
                        modifier = Modifier.weight(1f),
                        value = state.gw,
                        onValueChange = {
                            viewModel.intent(ControllerEthViewIntent.OnGwChange(it))
                        },
                        transform = { it },
                        keyboardType = KeyboardType.Number,
                        placeholder = state.gwOld,
                        enabled = enabled
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Физический адрес",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                    TextFieldItem(
                        modifier = Modifier.weight(1f),
                        value = state.mac,
                        onValueChange = {
                            viewModel.intent(ControllerEthViewIntent.OnMacChange(it))
                        },
                        transform = { it },
                        keyboardType = KeyboardType.Text,
                        placeholder = state.macOld
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.intent(ControllerEthViewIntent.Launch)
    }
}