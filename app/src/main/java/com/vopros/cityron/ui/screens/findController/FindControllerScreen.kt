package com.vopros.cityron.ui.screens.findController

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.vopros.cityron.local.Info
import com.vopros.cityron.navigation.FindControllerNavGraph

@FindControllerNavGraph(start = true)
@Destination
@Composable
fun FindControllerScreen(
    viewModel: FindControllerViewModel = hiltViewModel()
) {
    val infoState = viewModel.info.collectAsState()
    Column {
        when (val infos = infoState.value) {
            null -> {}
            emptyList<Info>() -> Text(text = "Контроллеры не найдены")
            else -> {
                LazyColumn {
                    items(infos) {

                    }
                }
            }
        }
        Button(onClick = { /*TODO*/ }) {

        }
    }
}