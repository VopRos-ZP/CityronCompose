package ru.cityron.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.cityron.R
import ru.cityron.ui.theme.Placeholder

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    visibility: Boolean,
    onVisibilityChange: () -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
) {
    val state by remember(visibility) {
        mutableStateOf(
            when (visibility) {
                true -> R.drawable.eye to VisualTransformation.None
                else -> R.drawable.eye_slash to PasswordVisualTransformation()
            }
        )
    }
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(4.dp),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = onVisibilityChange) {
                Icon(
                    painter = painterResource(id = state.first),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        placeholder = {
            Text(
                text = "Введите пароль",
                style = MaterialTheme.typography.body1
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = state.second,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.onPrimary,
            cursorColor = MaterialTheme.colors.onPrimary,
            backgroundColor = MaterialTheme.colors.primary,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            placeholderColor = Placeholder,
            trailingIconColor = MaterialTheme.colors.onPrimary
        )
    )
}
