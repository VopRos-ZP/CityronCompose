package com.vopros.cityron.m3.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vopros.cityron.navigation.MainNavGraph
import com.vopros.cityron.ui.components.Loading
import kotlinx.serialization.json.Json

@MainNavGraph
@Destination
@Composable
fun M3Screen(
    navigator: DestinationsNavigator,
    controllerItemString: String = "",
    viewModel: M3ScreenViewModel = hiltViewModel(),
) {
    val stateState = viewModel.state.collectAsState()
    when (val state = stateState.value) {
        null -> Loading()
        else -> {
            LazyColumn {
                item {
                    ControllerStateItem(title = "Старт/Стоп") {
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Запустить")
                        }
                    }
                }
                item {
                    ControllerStateItem(title = "Уставка, С") {
                        TextField(
                            value = "${state.state.set.temp / 10}",
                            onValueChange = {
                                // viewModel.send(it.toInt() * 10)
                            }
                        )
                    }
                }
                item {
                    var expanded by remember { mutableStateOf(false) }
                    ControllerStateItem(title = "Режим работы") {
                        Text(
                            text = "",
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            DropdownMenuItem(text = { Text(text = "Зима") }, onClick = { /*TODO*/ })
                            DropdownMenuItem(text = { Text(text = "Лето") }, onClick = { /*TODO*/ })
                        }
                    }
                }
                item {
                    var expanded by remember { mutableStateOf(false) }
                    ControllerStateItem(title = "Управление режимом Зима/Лето") {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(text = { Text(text = "Зима") }, onClick = { /*TODO*/ })
                            DropdownMenuItem(text = { Text(text = "Лето") }, onClick = { /*TODO*/ })
                        }
                    }
                }
                item {
                    var expanded by remember { mutableStateOf(false) }
                    ControllerStateItem(title = "Скорость вентилятора") {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            (1..5).map {
                                DropdownMenuItem(text = { Text(text = "$it") }, onClick = { /*TODO*/ })
                            }
                        }
                    }
                }
            }
        }
    }
    DisposableEffect(Unit) {
        viewModel.listenState(Json.decodeFromString(controllerItemString))
        onDispose { viewModel.disposeState() }
    }
}

@Composable
fun ControllerStateItem(
    title: String,
    right: @Composable RowScope.() -> Unit
) {
    Row {
        Text(text = title)
        Spacer(modifier = Modifier.weight(1f))
        right(this)
    }
}