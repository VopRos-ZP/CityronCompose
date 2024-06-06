package ru.cityron.presentation.screens.changeName

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cityron.presentation.components.BackScaffold

@Composable
fun ChangeNameScreen(
    onClick: () -> Unit,
    viewModel: ChangeNameViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    BackScaffold(
        title = "Имя контроллера",
        onClick = onClick,
        snackbarHostState = snackbarHostState,
        bottomBar = {
            AnimatedContent(targetState = state.name.isNotEmpty(), label = "") {
                when (it) {
                    true -> BottomAppBar(
                        modifier = Modifier.clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
                        backgroundColor = MaterialTheme.colors.primary,
                        contentPadding = PaddingValues(20.dp),
                    ) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(4.dp),
                            contentPadding = PaddingValues(vertical = 14.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = MaterialTheme.colors.onBackground
                            ),
                            onClick = viewModel::onSaveClick
                        ) {
                            Text(text = "Сохранить")
                        }
                    }
                    else -> {}
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onNameChange(it) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeholder = { Text(text = "Введите имя контроллера") },
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
    }
    LaunchedEffect(key1 = state) {
        if (state.isErrorChecked == true && state.isShowSnackbar) {
            snackbarHostState.showSnackbar(message = "Устройство не было найдено.")
            viewModel.closeSnackbar()
        }
        if (state.isErrorChecked == false) {
            snackbarHostState.showSnackbar(
                message = "Устройство успешно добавлено в список.",
                duration = SnackbarDuration.Short
            )
            onClick()
        }
    }
}