package ru.cityron.presentation.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import ru.cityron.R
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.navigation.Screen

@Composable
fun AuthScreen(
    onClick: () -> Unit,
    onAuthRoleClick: (Screen) -> Unit,
) {
    val authRoles = stringArrayResource(id = R.array.auth_role)
    BackScaffold(
        title = "Вход",
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 30.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            authRoles.mapIndexed { i, text ->
                AuthItem(
                    onClick = { onAuthRoleClick(Screen.AuthRole(i)) },
                    text = text,
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun AuthItem(
    onClick: () -> Unit,
    text: String,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = MaterialTheme.colors.onPrimary
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        onClick = onClick,
        enabled = true,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            disabledBackgroundColor = backgroundColor
        ),
        contentPadding = PaddingValues(20.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}