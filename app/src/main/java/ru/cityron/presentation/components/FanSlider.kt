package ru.cityron.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun FanSlider(
    values: List<Int>,
    value: Int,
    onFinishChange: (Int) -> Unit,
    onValueChange: (Int) -> Unit = {},
) {
    val stepCount = values.size - 1
    val primary = MaterialTheme.colors.primary
    val variant = MaterialTheme.colors.primaryVariant

    var positionValue by remember { mutableIntStateOf(value) }
    var isTap by remember { mutableStateOf(false) }
    var isDragging by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = value) {
        positionValue = value
    }
    LaunchedEffect(key1 = isDragging, key2 = positionValue) {
        if (!isDragging && positionValue != value) {
            onFinishChange(positionValue)
        }
    }
    LaunchedEffect(key1 = isTap, key2 = positionValue) {
        if (isTap && positionValue != value) {
            onFinishChange(positionValue)
        }
    }

    val calcPositionValueByOffset: PointerInputScope.(Offset) -> Unit = {
        val stepWidth = size.width / stepCount
        val newValue = (it.x / stepWidth).roundToInt() + 1
        positionValue = newValue.coerceIn(values.first(), values.last())
        onValueChange(positionValue)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .padding(horizontal = 20.dp)
            .pointerInput(Unit) {
                detectTapGestures {
                    isTap = false
                    calcPositionValueByOffset(it)
                    isTap = true
                }
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isDragging = true },
                    onDragEnd = { isDragging = false },
                    onDrag = { it, _ -> calcPositionValueByOffset(it.position) }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 20f
            val thumbRadius = 30f

            drawLine(
                color = variant,
                start = Offset(x = 0f, y = center.y),
                end = Offset(x = size.width, y = center.y),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )

            val stepWidth = size.width / stepCount
            values.forEachIndexed { index, _ ->
                drawCircle(
                    color = primary,
                    radius = 8f,
                    center = Offset(index * stepWidth, size.height / 2)
                )
            }

            drawCircle(
                color = variant,
                radius = thumbRadius,
                center = Offset(values.indexOf(positionValue) * stepWidth, center.y)
            )
            if (isDragging) {
                drawCircle(
                    color = variant,
                    radius = thumbRadius * 2f,
                    center = Offset(values.indexOf(positionValue) * stepWidth, center.y),
                    alpha = 0.25f
                )
            }

        }
    }
}