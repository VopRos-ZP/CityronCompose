package ru.cityron.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.cityron.presentation.components.BackScaffold

@Composable
fun AddCustomScreen(
    onClick: () -> Unit,
    onNextClick: () -> Unit
) {
    var ip by remember { mutableStateOf("") }
    val isCorrect by remember(ip) { mutableStateOf(isValidIPAddress(ip)) }
    BackScaffold(title = "Добавить вручную", onClick = onClick) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            OutlinedTextField(
                value = ip,
                onValueChange = { ip = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = { Text(text = "Введите IP-адрес устройства") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.primary,
                    placeholderColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.3f),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = MaterialTheme.colors.onPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            )
            TextButton(
                onClick = onNextClick,
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = when (isCorrect) {
                        true -> MaterialTheme.colors.secondary
                        else -> Color(0xFF383838)
                    },
                    disabledContentColor = MaterialTheme.colors.onPrimary,
                    contentColor = MaterialTheme.colors.onPrimary
                ),
                enabled = isCorrect,
                contentPadding = PaddingValues(vertical = 14.dp),
            ) {
                Text(text = "Далее")
            }
        }
    }
}

fun isValidIPAddress(ip: String): Boolean {
    val ipRegex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    return ip.matches(ipRegex.toRegex())
}