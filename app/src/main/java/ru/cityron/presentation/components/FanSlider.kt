package ru.cityron.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.math.roundToInt

@Composable
fun FanSlider(
    modifier: Modifier = Modifier,
    fan: Int,
    onFanChange: (Int) -> Unit,
    onFanChangeFinished: (Int) -> Unit = {}
) {
    Slider(
        modifier = modifier,
        value = fan.toFloat(),
        onValueChange = {
            if (it in (it - 0.49f)..<(it + 0.5f))
            onFanChange(it.roundToInt())
        },
        steps = 3,
        valueRange = 1f..5f,
        colors = SliderDefaults.colors(
            inactiveTickColor = MaterialTheme.colors.primary,
            activeTickColor = MaterialTheme.colors.primary,
            thumbColor = MaterialTheme.colors.primaryVariant,
            activeTrackColor = MaterialTheme.colors.primaryVariant,
            inactiveTrackColor = MaterialTheme.colors.primaryVariant,
        ),
        onValueChangeFinished = {
            onFanChangeFinished(fan)
        }
    )
}