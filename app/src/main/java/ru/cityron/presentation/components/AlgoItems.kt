package ru.cityron.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.cityron.R
import ru.cityron.domain.utils.toInt
import kotlin.math.exp

@Composable
fun AlgoNumberItem(
    text: String,
    textUnit: String? = null,
    value: Int,
    enabled: Boolean = true,
    onValueChange: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.primary)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(0.5f),
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.width(24.dp))
        TextFieldItem(
            modifier = Modifier
                .widthIn(max = 96.dp)
                .heightIn(min = 44.dp),
            value = value,
            onValueChange = onValueChange,
            transform = { it.toInt() },
            keyboardType = KeyboardType.Number,
            enabled = enabled
        )
        if (textUnit != null) {
            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = textUnit,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun AlgoBooleanItem(
    text: String,
    value: Int,
    onValueChange: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.primary)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(0.7f),
            style = MaterialTheme.typography.body1
        )
        Switch(
            modifier = Modifier.height(30.dp),
            checked = value == 1,
            onCheckedChange = { onValueChange(it.toInt()) },
        )
    }
}

@Composable
fun <T> TextFieldItem(
    modifier: Modifier = Modifier,
    value: T,
    onValueChange: (T) -> Unit,
    transform: (String) -> T,
    keyboardType: KeyboardType,
    enabled: Boolean = true,
    placeholder: String? = null
) {
    var textValue by remember(value) { mutableStateOf("$value") }
    var isFocused by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .background(
                color = if (isFocused) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.background,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = if (isFocused) MaterialTheme.colors.primaryVariant else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .onFocusChanged { isFocused = it.isFocused }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                textStyle = MaterialTheme.typography.body1.copy(
                    color = if (enabled) MaterialTheme.colors.onPrimary else MaterialTheme.colors.background,
                    textAlign = TextAlign.Center,
                ),
                enabled = enabled,
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colors.onPrimary),
                value = textValue,
                onValueChange = { textValue = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            )
            if (placeholder != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = placeholder,
                    color = MaterialTheme.colors.onPrimary.copy(alpha = 0.1f),
                    style = MaterialTheme.typography.body1.copy(
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
    LaunchedEffect(textValue) {
        onValueChange(transform(textValue))
    }
}

@Composable
fun <T> OutlinedTextFieldItem(
    modifier: Modifier = Modifier,
    value: T,
    onValueChange: (T) -> Unit,
    transform: (String) -> T,
    keyboardType: KeyboardType,
    placeholder: String? = null
) {
    var textValue by remember { mutableStateOf("$value") }
    var isFocused by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier.onFocusChanged { isFocused = it.isFocused },
        value = textValue,
        onValueChange = { textValue = it },
        textStyle = MaterialTheme.typography.body1.copy(
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Center,
        ),
        shape = RoundedCornerShape(4.dp),
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = placeholder ?: "",
                textAlign = TextAlign.Center
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.onPrimary,
            backgroundColor = if (isFocused) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.background,
            cursorColor = MaterialTheme.colors.onPrimary,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = MaterialTheme.colors.primaryVariant,
            placeholderColor = MaterialTheme.colors.primary
        )
    )
    LaunchedEffect(textValue) {
        onValueChange(transform(textValue))
    }
}

@Composable
fun <T> DropDownMenu(
    modifier: Modifier = Modifier,
    value: T,
    onValueChange: (T) -> Unit,
    format: (T) -> String,
    items: List<T>,
) {
    val textValue by remember(value) { mutableStateOf(format(value)) }
    var isFocused by remember { mutableStateOf(false) }
    val expanded by remember(isFocused) { mutableStateOf(isFocused) }
    Box(
        modifier = modifier
            .background(
                color = if (isFocused) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.background,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = if (isFocused) MaterialTheme.colors.primaryVariant else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { isFocused = true }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { isFocused = it.isFocused },
                textStyle = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Center,
                ),
                readOnly = true,
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colors.onPrimary),
                value = textValue,
                onValueChange = {},
                keyboardOptions = KeyboardOptions.Default,
            )
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(if (expanded) -90f else 90f)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { isFocused = false },
        ) {
            items.forEach {
                DropdownMenuItem(
                    onClick = { onValueChange(it) }
                ) {
                    Text(text = format(it))
                }
            }
        }
    }
}
