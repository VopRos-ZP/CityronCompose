package ru.cityron.presentation.screens.auth.role

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.R
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.CodeField
import ru.cityron.presentation.components.Loader
import ru.cityron.presentation.navigation.Screen

@Composable
fun AuthRoleScreen(
    onClick: () -> Unit,
    onNavigate: (Screen) -> Unit,
    accessLevel: String,
    viewModel: AuthRoleViewModel = hiltViewModel()
) {
    val index = stringArrayResource(id = R.array.auth_role_short).indexOf(accessLevel)
    val roleTitled = stringArrayResource(id = R.array.auth_role_title)[index]
    val state by viewModel.state().collectAsState()
    val viewAction = viewModel.action().collectAsState(initial = null)

    viewAction.value?.let {
        when (it) {
            is AuthRoleViewAction.Success -> onNavigate(Screen.Blank)
        }
    }

    BackScaffold(
        title = "Подключение к контроллеру",
        onClick = onClick
    ) {
        if (state.isLoading) {
            Loader()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp, start = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.spacedBy(72.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Для входа под $roleTitled необходимо ввести пароль.",
                    style = MaterialTheme.typography.h4.copy(
                        textAlign = TextAlign.Center
                    )
                )
                CodeField(
                    value = state.password,
                    length = state.length,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                    onValueChange = { viewModel.intent(AuthRoleViewIntent.OnPasswordChange(it)) }
                )
            }
        }
    }
    DisposableEffect(Unit) {
        viewModel.intent(AuthRoleViewIntent.Launch(accessLevel))
        onDispose { viewModel.intent(AuthRoleViewIntent.OnDispose) }
    }
    LaunchedEffect(state.password) {
        if (state.password.length == state.length) {
            viewModel.intent(AuthRoleViewIntent.OnPasswordChangeFinish(accessLevel))
        }
    }
}