package ru.cityron.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import ru.cityron.domain.utils.Temp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Thermostat(
    temp: Int,
) {
    val background = MaterialTheme.colors.background
    val primary = MaterialTheme.colors.background
    val secondary = MaterialTheme.colors.background

    Box(
        modifier = Modifier.size(200.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val arcStrokeWidth = 10f
            val thumbRadius = 15f
            drawArc(
                color = background,
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = arcStrokeWidth)
            )

            // Draw the active arc
            drawArc(
                color = secondary,
                startAngle = 135f,
                sweepAngle = 135f, // Adjust this value to reflect the actual value
                useCenter = false,
                style = Stroke(width = arcStrokeWidth)
            )

            // Draw the thumb
            val thumbAngle = Math.toRadians(135.0 + 135.0) // Adjust this to match the active arc end angle
            val thumbX = center.x + cos(thumbAngle) * (size.minDimension / 2 - arcStrokeWidth / 2)
            val thumbY = center.y + sin(thumbAngle) * (size.minDimension / 2 - arcStrokeWidth / 2)
            drawCircle(
                color = secondary,
                radius = thumbRadius,
                center = Offset(thumbX.toFloat(), thumbY.toFloat())
            )

            // Draw the temperature texts
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    Temp.toGrade(temp),
                    center.x,
                    center.y - 20,
                    android.graphics.Paint().apply {
                        color = primary.value.toInt()
                        textSize = 40f
                        textAlign = android.graphics.Paint.Align.CENTER
                        isAntiAlias = true
                    }
                )

                drawText(
                    "24.5",
                    center.x,
                    center.y + 50,
                    android.graphics.Paint().apply {
                        color = secondary.value.toInt()
                        textSize = 30f
                        textAlign = android.graphics.Paint.Align.CENTER
                        isAntiAlias = true
                    }
                )
            }
        }
    }
}