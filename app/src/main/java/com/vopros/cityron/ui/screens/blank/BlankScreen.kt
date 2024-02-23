package com.vopros.cityron.ui.screens.blank

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.vopros.cityron.navigation.MainNavGraph
import com.vopros.cityron.ui.theme.LocalDrawer
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@MainNavGraph(start = true)
@Destination
@Composable
fun BlankScreen() {
    val scope = rememberCoroutineScope()
    val drawer = LocalDrawer.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = "Выберите контроллер")
            Button(onClick = { scope.launch { drawer.open() } }) {
                Text(text = "Выбрать")
            }
        }
    }
}