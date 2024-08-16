package ru.cityron.presentation.screens.addController

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.presentation.components.BackScaffold
import ru.cityron.presentation.components.CodeField
import ru.cityron.presentation.navigation.Screen

@Composable
fun AddControllerScreen(
    onClick: () -> Unit,
    onNavigateClick: (Screen) -> Unit,
    accessLevel: String,
    viewModel: AddControllerViewModel = hiltViewModel()
) {
    val state by viewModel.state().collectAsState()
    val viewAction = viewModel.action().collectAsState(initial = null)

    viewAction.value?.let {
        when (it) {
            is AddControllerViewAction.Success -> onNavigateClick(Screen.AuthRole(accessLevel))
        }
    }

    BackScaffold(
        title = "Добавить контроллер",
        onClick = {
            viewModel.intent(AddControllerViewIntent.OnDispose)
            onClick()
        }
    ) {
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
                value = state.code,
                onValueChange = { viewModel.intent(AddControllerViewIntent.OnCodeChange(it)) },
                length = 4,
                cursorColor = MaterialTheme.colors.primaryVariant
            )
        }
    }
    LaunchedEffect(Unit) {
        viewModel.intent(AddControllerViewIntent.Launch(accessLevel))
    }
    LaunchedEffect(state.code) {
        if (state.code.length == 4) {
            viewModel.intent(AddControllerViewIntent.OnCodeChangeFinish)
        }
    }
}

