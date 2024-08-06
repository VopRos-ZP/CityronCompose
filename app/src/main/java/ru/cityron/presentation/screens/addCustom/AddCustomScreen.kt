package ru.cityron.presentation.screens.addCustom

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.presentation.components.BackScaffoldWithState
import ru.cityron.presentation.components.rememberSnackbarState

@Composable
fun AddCustomScreen(
    onClick: () -> Unit,
    onNextClick: () -> Unit,
    viewModel: AddCustomViewModel = hiltViewModel()
) {
    val stateState = viewModel.state.collectAsState()
    val snackbarState = rememberSnackbarState(result = stateState.value?.result)
    BackScaffoldWithState(
        title = "Добавить вручную",
        onClick = onClick,
        state = stateState,
        snackbarState = snackbarState
    ) { state ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            OutlinedTextField(
                value = state.ip,
                onValueChange = { viewModel.intent(AddCustomViewIntent.OnIpChange(it)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = MaterialTheme.typography.h4,
                placeholder = { Text(
                    text = "Введите IP-адрес устройства",
                    style = MaterialTheme.typography.h4
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
            TextButton(
                onClick = { viewModel.intent(AddCustomViewIntent.OnNextClick) },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = when (state.isCorrect) {
                        true -> MaterialTheme.colors.secondary
                        else -> Color(0xFF383838)
                    },
                    disabledContentColor = MaterialTheme.colors.onPrimary,
                    contentColor = MaterialTheme.colors.onPrimary
                ),
                enabled = state.isCorrect,
                contentPadding = PaddingValues(vertical = 14.dp),
            ) {
                Text(
                    text = "Далее",
                    style = MaterialTheme.typography.body2
                )
            }
        }
        LaunchedEffect(state) {
            if (state.result != null) {
                snackbarState.showSnackbar {
                    viewModel.intent(AddCustomViewIntent.OnSnackbarResultChange(null))
                    if (!state.result.isError) {
                        onNextClick()
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.intent(AddCustomViewIntent.Launch)
    }
}
