package ru.cityron.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Snackbar(snackbarData: SnackbarData) {
    androidx.compose.material.Snackbar(
        modifier = Modifier,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = MaterialTheme.colors.primary,
        snackbarData = snackbarData
    )
}