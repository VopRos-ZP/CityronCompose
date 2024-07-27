package ru.cityron.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun BottomSaveButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BottomAppBar(
        modifier = modifier.clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
        backgroundColor = MaterialTheme.colors.primary,
        contentPadding = PaddingValues(20.dp),
        elevation = 10.dp
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            contentPadding = PaddingValues(vertical = 14.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onBackground
            ),
            onClick = onClick
        ) {
            Text(
                text = "Сохранить",
                style = MaterialTheme.typography.body2
            )
        }
    }
}