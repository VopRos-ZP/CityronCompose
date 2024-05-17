package ru.cityron.presentation.screens.addController

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.CodeField

@Composable
fun AddControllerScreen(
    onClick: () -> Unit
) {
    var code by remember { mutableStateOf("") }
    BackScaffold(title = "Добавить контроллер", onClick = onClick) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            Text(
                text = "Введите четырех знанчный код,\nотображаемый на экране устройства",
                textAlign = TextAlign.Center
            )
            CodeField(
                value = code,
                onValueChange = { code = it },
                length = 4,
                cursorColor = MaterialTheme.colors.primaryVariant
            )
        }
    }
    // checks verification code
    LaunchedEffect(key1 = code) {
        // if confirmed goto LoginScreen
    }
}

