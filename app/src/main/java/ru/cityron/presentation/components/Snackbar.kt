package ru.cityron.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ru.cityron.presentation.mvi.SnackbarResult

@Composable
fun Snackbar(
    result: SnackbarResult?,
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
        backgroundColor = if (result?.isError == true) MaterialTheme.colors.error else MaterialTheme.colors.primaryVariant,
        contentColor = if (result?.isError == true) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary,
        content = {
            Text(
                text = snackbarData.message,
                style = MaterialTheme.typography.body1
            )
        }
    )
}

data class SnackbarState(
    private val context: Context,
    val snackbarHostState: SnackbarHostState,
    val snackbarResult: SnackbarResult? = null,
) {

    suspend fun showSnackbar(onClose: () -> Unit = {}, ) {
        if (snackbarHostState.showSnackbar(message = context.getString(snackbarResult!!.label))
            == androidx.compose.material.SnackbarResult.Dismissed
        ) { onClose() }
    }

}

@Composable
fun rememberSnackbarState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    result: SnackbarResult? = null,
): SnackbarState {
    val context = LocalContext.current
    return remember(result) { SnackbarState(context, snackbarHostState, result) }
}