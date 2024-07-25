package ru.cityron.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Switch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    androidx.compose.material.Switch(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        colors = SwitchDefaults.colors(
            checkedTrackAlpha = 0.5f,
            checkedThumbColor = MaterialTheme.colors.primaryVariant,
            checkedTrackColor = MaterialTheme.colors.primaryVariant,
            uncheckedTrackAlpha = 1f,
            uncheckedThumbColor = MaterialTheme.colors.onPrimary,
            uncheckedTrackColor = MaterialTheme.colors.background,
        )
    )
}