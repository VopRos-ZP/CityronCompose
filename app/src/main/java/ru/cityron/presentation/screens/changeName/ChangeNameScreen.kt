package ru.cityron.presentation.screens.changeName

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.presentation.components.BackScaffoldWithState
import ru.cityron.presentation.components.BottomSaveButton
import ru.cityron.presentation.components.rememberSnackbarState

@Composable
fun ChangeNameScreen(
    onClick: () -> Unit,
    viewModel: ChangeNameViewModel = hiltViewModel()
) {
    val stateState = viewModel.state.collectAsState()
    val snackbarState = rememberSnackbarState(result = stateState.value?.result)
    BackScaffoldWithState(
        title = "Имя контроллера",
        onClick = onClick,
        snackbarState = snackbarState,
        state = stateState,
        bottomBar = {
            if (stateState.value?.name != stateState.value?.oldName) {
                BottomSaveButton(
                    onClick = { viewModel.intent(ChangeNameViewIntent.OnSaveClick) }
                )
            }
        }
    ) { state ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.intent(ChangeNameViewIntent.OnNameChange(it)) },
                singleLine = true,
                textStyle = MaterialTheme.typography.h4,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeholder = { Text(
                    text = "Введите имя контроллера",
                    style = MaterialTheme.typography.h4,
                ) },
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
        }
        LaunchedEffect(state) {
            if (state.result != null) {
                snackbarState.showSnackbar {
                    viewModel.intent(ChangeNameViewIntent.OnSnakbarResultChange(null))
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.intent(ChangeNameViewIntent.Launch)
    }
}