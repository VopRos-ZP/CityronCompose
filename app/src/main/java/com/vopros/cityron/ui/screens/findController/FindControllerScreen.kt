package com.vopros.cityron.ui.screens.findController

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.vopros.cityron.local.Info
import com.vopros.cityron.navigation.FindControllerNavGraph
import com.vopros.cityron.ui.screens.Screen
import com.vopros.cityron.ui.theme.LocalToolbar

@FindControllerNavGraph(start = true)
@Destination
@Composable
fun FindControllerScreen(
    viewModel: FindControllerViewModel = hiltViewModel()
) {
    val infoState = viewModel.info.collectAsState()
    Screen(title = "Найти контроллер") {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (val infos = infoState.value) {
                null -> {}
                emptyList<Info>() -> Text(text = "Контроллеры не найдены")
                else -> {
                    LazyColumn {
                        items(infos) { (info, isAdded) ->
                            FindControllerItem(info, isAdded) {
                                viewModel.addController(info)
                            }
                        }
                    }
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                onClick = { viewModel.toggleServer() }
            ) {
                Text(text = when (viewModel.isServerRunning) {
                    true -> "Остановить поиск"
                    else -> "Найти"
                })
            }
        }
    }
    DisposableEffect(Unit) {
        onDispose { viewModel.stopServer() }
    }
}

@Composable
fun FindControllerItem(info: Info, isAdded: Boolean, onClick: () -> Unit) {
    Card {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${info.devName} (${info.idUsr})",
                    color = Color.Black
                )
                Text(
                    text = when (info.name.isEmpty()) {
                        true -> "Нет имени"
                        else -> info.name
                    },
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onClick,
                enabled = !isAdded
            ) {
                Text(text = "Добавить")
            }
        }
    }
}

@Preview
@Composable
fun FindControllerItem_not_added_Preview() {
    FindControllerItem(
        info = Info(
            "info",
            0,
            "123456",
            "eisdlvmepdoksda",
            "m3",
            "",
            0, 1,
            "192.168.1.100",
            "255.255.255.0",
            "192.168.1.100"
        ),
        isAdded = false
    ) {}
}

@Preview
@Composable
fun FindControllerItem_added_Preview() {
    FindControllerItem(
        info = Info(
            "info",
            0,
            "123456",
            "eisdlvmepdoksda",
            "m3",
            "Office",
            0, 1,
            "192.168.1.100",
            "255.255.255.0",
            "192.168.1.100"
        ),
        isAdded = true
    ) {}
}