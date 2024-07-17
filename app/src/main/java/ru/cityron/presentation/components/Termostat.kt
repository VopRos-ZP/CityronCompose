package ru.cityron.presentation.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toOffset
import ru.cityron.domain.utils.Temp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Thermostat(
    modifier: Modifier = Modifier,
    value: Int,
    minValue: Int = 50,
    maxValue: Int = 450,
    step: Int = 5,
    onPositionChange: (Int) -> Unit = {},
) {
    val background = MaterialTheme.colors.secondary.copy(alpha = 0.3f)
    val secondary = MaterialTheme.colors.secondary

    var circleCenter by remember { mutableStateOf(Offset.Zero) }
    var positionValue by remember { mutableIntStateOf(value) }

    LaunchedEffect(key1 = Unit) {
        positionValue = value
    }
    LaunchedEffect(key1 = positionValue) {
        onPositionChange(positionValue)
    }

    val startAngle = 210f
    val angleRange = -240f

    Box(
        modifier = modifier.width(335.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        val offset = change.position - size.center.toOffset()
                        val angle = (atan2(offset.y, offset.x) * (180 / PI).toFloat() + 360) % 360
                        if (angle in 0f..210f || angle >= 330) {

                        }
                    }
                }
        ) {
            val width = size.width
            val height = size.height

            circleCenter = Offset(x = width / 2f, y = height / 2f)

            val arcStrokeWidth = 20f
            val thumbRadius = 30f
            val radius = size.minDimension / 2

            drawArc(
                color = background,
                startAngle = startAngle,
                sweepAngle = angleRange,
                useCenter = false,
                style = Stroke(width = arcStrokeWidth)
            )

            drawArc(
                color = secondary,
                startAngle = startAngle,
                sweepAngle = (positionValue - minValue) * angleRange / (maxValue - minValue),
                useCenter = false,
                style = Stroke(width = arcStrokeWidth)
            )

            val indicatorAngle = startAngle + ((positionValue - minValue) * angleRange / (maxValue - minValue))
            val indicatorX = center.x + cos(Math.toRadians(indicatorAngle.toDouble())).toFloat() * radius
            val indicatorY = center.y + sin(Math.toRadians(indicatorAngle.toDouble())).toFloat() * radius

            drawCircle(
                color = secondary,
                radius = thumbRadius,
                center = Offset(indicatorX, indicatorY)
            )
            // Draw the temperature values
            for (i in minValue..maxValue step step) {
                val angle = startAngle + (i - minValue) * angleRange / (maxValue - minValue)
                val textRadius = radius + 30.dp.toPx()
                val textX = center.x + cos(Math.toRadians(angle.toDouble())).toFloat() * textRadius
                val textY = center.y + sin(Math.toRadians(angle.toDouble())).toFloat() * textRadius
                drawContext.canvas.nativeCanvas.drawText(
                    "${i.div(10)}",
                    textX,
                    textY,
                    Paint().apply {
                        color = android.graphics.Color.WHITE
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = 40f
                        isAntiAlias = true
                    }
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "",//Temp.toGrade(value),
                color = Color.Transparent,
                fontSize = 62.sp
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(0.5f))
                Text(
                    text = Temp.toGrade(value),
                    color = MaterialTheme.colors.secondary,
                    fontSize = 31.sp
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}