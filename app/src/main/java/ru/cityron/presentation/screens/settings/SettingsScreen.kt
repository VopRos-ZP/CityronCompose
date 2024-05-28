package ru.cityron.presentation.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.cityron.presentation.components.BackScaffold

@Composable
fun SettingsScreen(
    onClick: () -> Unit
) {
    BackScaffold(title = "Настройки", onClick = onClick) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(17.dp)
        ) {

        }
    }
}

@Composable
fun Category(
    icon: Int,
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(),
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = null)
        Text(text = text)
    }
}