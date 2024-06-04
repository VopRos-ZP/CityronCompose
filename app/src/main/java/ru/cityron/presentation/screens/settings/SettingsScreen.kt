package ru.cityron.presentation.screens.settings

import androidx.compose.foundation.clickable
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
import ru.cityron.R
import ru.cityron.presentation.components.BackScaffold

@Composable
fun SettingsScreen(
    onClick: () -> Unit,
    onChangeName: () -> Unit,
    onAuthClick: () -> Unit,
    onAlgoClick: () -> Unit,
    onAlarmClick: () -> Unit,
    onControllerClick: () -> Unit
) {
    BackScaffold(title = "Настройки", onClick = onClick) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(17.dp)
        ) {
            Category(
                icon = R.drawable.edit,
                text = "Редактировать Имя контроллера",
                onClick = onChangeName
            )
            Category(
                icon = R.drawable.user,
                text = "Сменить пользователя",
                onClick = onAuthClick
            )
            Category(
                icon = R.drawable.algo,
                text = "Алгоритм",
                onClick = onAlgoClick
            )
            Category(
                icon = R.drawable.danger,
                text = "Аварии",
                onClick = onAlarmClick
            )
            Category(
                icon = R.drawable.setting_2,
                text = "Контроллер",
                onClick = onControllerClick
            )
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
        modifier = Modifier
            .fillMaxWidth()
            .padding()
            .clickable { onClick() },
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = null)
        Text(text = text)
    }
}