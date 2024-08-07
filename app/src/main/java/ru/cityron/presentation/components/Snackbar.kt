package ru.cityron.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarData
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.cityron.presentation.mvi.SnackbarResult

@Composable
fun Snackbar(
    result: SnackbarResult,
    snackbarData: SnackbarData
) {
    val actionLabel = snackbarData.actionLabel
    val actionComposable: (@Composable () -> Unit)? = if (actionLabel != null) {
        @Composable {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.primary),
                onClick = { snackbarData.performAction() },
                content = { Text(actionLabel) }
            )
        }
    } else {
        null
    }
    androidx.compose.material.Snackbar(
        modifier = Modifier.padding(12.dp),
        action = actionComposable,
        actionOnNewLine = false,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = if (result.isError) MaterialTheme.colors.error else MaterialTheme.colors.primaryVariant,
        contentColor = if (result.isError) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary,
        content = {
            Text(
                text = snackbarData.message,
                style = MaterialTheme.typography.body1
            )
        }
    )
}

@Composable
fun rememberSnackbarResult() = remember {
    mutableStateOf<SnackbarResult?>(null)
}
