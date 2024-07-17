package ru.cityron.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(17.dp)
        ) {
            Category(
                icon = R.drawable.edit,
                text = "Редактировать имя контроллера",
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
            .clip(RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colors.primary)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp
        )
    }
}