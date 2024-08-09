package ru.cityron.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CodeField(
    value: String,
    length: Int,
    modifier: Modifier = Modifier,
    boxWidth: Dp = 36.dp,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit,
    cursorColor: Color = MaterialTheme.colors.onPrimary,
    spaceBetweenBoxes: Dp = 12.dp,
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        onValueChange = {
            if (it.length <= length) {
                onValueChange(it)
            }
        },
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(cursorColor),
        decorationBox = {
            Row(
                modifier = Modifier.width(boxWidth * length + spaceBetweenBoxes * (length - 1)),
                horizontalArrangement = Arrangement.spacedBy(spaceBetweenBoxes),
            ) {
                repeat(length) { index ->
                    Column(
                        modifier = Modifier.width(boxWidth),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = value.getOrNull(index)?.toString() ?: "",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h1.copy(lineHeight = 24.sp)
                        )
                        Box(
                            modifier = Modifier
                                .size(boxWidth, 3.dp)
                                .background(
                                    color = MaterialTheme.colors.primaryVariant,
                                    shape = RoundedCornerShape(3.dp)
                                )
                        )
                    }
                }
            }
        }
    )
}