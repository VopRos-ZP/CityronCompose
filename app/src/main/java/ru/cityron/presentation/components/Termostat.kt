package ru.cityron.presentation.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.cityron.domain.utils.Temp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Thermostat(
    modifier: Modifier = Modifier,
    initValue: Int,
    value: Int,
    minValue: Int = 0,
    maxValue: Int = 100,
    step: Int = 1,
    onPositionChange: (Int) -> Unit = {},
) {
    val background = MaterialTheme.colors.secondary.copy(alpha = 0.3f)
    val primary = MaterialTheme.colors.primary
    val secondary = MaterialTheme.colors.secondary
    val variant = MaterialTheme.colors.primaryVariant

    var circleCenter by remember { mutableStateOf(Offset.Zero) }
    var positionValue by remember { mutableIntStateOf(value) }

    LaunchedEffect(key1 = positionValue) {
        onPositionChange(positionValue)
    }

    Box(modifier = modifier.width(335.dp)) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height

            circleCenter = Offset(x = width / 2f, y = height / 2f)

            val arcStrokeWidth = 20f
            val thumbRadius = 30f

            drawArc(
                color = background,
                startAngle = 205f,
                sweepAngle = -230f,
                useCenter = false,
                style = Stroke(width = arcStrokeWidth)
            )
            drawArc(
                color = secondary,
                startAngle = 205f,
                sweepAngle = -20f, // Adjust this value to reflect the actual value
                useCenter = false,
                style = Stroke(width = arcStrokeWidth)
            )

            // Draw the thumb
            val thumbAngle = Math.toRadians(205.0) // Adjust this to match the active arc end angle
            val thumbX = center.x + cos(thumbAngle) * (size.minDimension / 2 - arcStrokeWidth / 2)
            val thumbY = center.y + sin(thumbAngle) * (size.minDimension / 2 - arcStrokeWidth / 2)
            drawCircle(
                color = secondary,
                radius = thumbRadius,
                center = Offset(thumbX.toFloat(), thumbY.toFloat())
            )
            drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText(
                        Temp.toGrade(initValue),
                        (circleCenter.x / 5) * 3,
                        circleCenter.y + 31.sp.toPx(),
                        Paint().apply {
                            textSize = 62.sp.toPx()
                            color = variant.toArgb()
                        }
                    )
                    drawText(
                        Temp.toGrade(value),
                        (circleCenter.x / 4) * 3,
                        circleCenter.y + (31.sp.toPx() * 2.75f),
                        Paint().apply {
                            textSize = 32.sp.toPx()
                            color = secondary.toArgb()
                        }
                    )
                }
            }

            for (i in (0..< ((maxValue - minValue)) / 2).step(step)) {
                val stepX = center.x + cos(thumbAngle * i) * (size.minDimension / 2 - arcStrokeWidth / 2)
                val stepY = center.y + sin(thumbAngle * i) * (size.minDimension / 2 - arcStrokeWidth / 2)
//                drawCircle(
//                    color = primary,
//                    radius = arcStrokeWidth,
//                    center = Offset(x = stepX.toFloat(), y = stepY.toFloat())
//                )
                drawContext.canvas.nativeCanvas.apply {
                    drawIntoCanvas {
                        drawText(
                            Temp.toGrade(value),
                            (circleCenter.x / 4) * 3,
                            circleCenter.y + (31.sp.toPx() * 2.75f),
                            Paint().apply {
                                textSize = 32.sp.toPx()
                                color = secondary.toArgb()
                            }
                        )
                    }
                }
            }

        }
    }
}