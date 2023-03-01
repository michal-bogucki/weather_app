@file:OptIn(ExperimentalTextApi::class)

package com.weatherapplication.feature.cityweather.presentation.view.components.weather

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapplication.core.element
import com.weatherapplication.core.textColor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Preview
@Composable
fun PreviewPorgressBar() {
    ComposeCircularProgressBar(
        percentage = 0.5f,
        fillColor = element,
        backgroundColor = Color(android.graphics.Color.parseColor("#90A4AE")),
        strokeWidth = 10.dp,
        sunrise = "07:30",
        sunset = "18:45",
    )
}

@Composable
fun ComposeCircularProgressBar(
    modifier: Modifier = Modifier,
    percentage: Float,
    fillColor: Color,
    backgroundColor: Color,
    strokeWidth: Dp,
    sunrise: String,
    sunset: String,
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = modifier
            .width(250.dp).height(120.dp)
            .padding(10.dp).padding(top = 20.dp),
    ) {
        drawArc(
            color = backgroundColor,
            210f,
            120f,
            false,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
            size = Size(size.width, size.width),
        )

        drawArc(
            color = fillColor,
            210f,
            percentage * 120f,
            false,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
            size = Size(size.width, size.width),
        )

        var angleInDegrees = percentage * 120f + 120.0
        var radius = (size.width / 2)
        var x = -(radius * kotlin.math.sin(Math.toRadians(angleInDegrees))).toFloat() + (size.width / 2)
        var y = (radius * kotlin.math.cos(Math.toRadians(angleInDegrees))).toFloat() + (size.width / 2)

        drawCircle(
            color = Color.White,
            radius = 10f,
            center = Offset(x, y),
        )

        var textLayoutResult: TextLayoutResult = textMeasurer.measure(
            text = AnnotatedString(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0,5)),
            style = TextStyle(fontSize = 12.sp, color = textColor),
        )
        var textSize = textLayoutResult.size

        drawText(
            textLayoutResult,
            topLeft = Offset(x - textSize.width / 2, y - 70),
        )

        angleInDegrees = 120.0

        x = -(radius * kotlin.math.sin(Math.toRadians(angleInDegrees))).toFloat() + (size.width / 2)
        y = (radius * kotlin.math.cos(Math.toRadians(angleInDegrees))).toFloat() + (size.width / 2)
        val center1 = Offset(x, y)
        textLayoutResult = textMeasurer.measure(
            text = AnnotatedString(sunrise),
            style = TextStyle(fontSize = 12.sp, color = textColor),
        )
        textSize = textLayoutResult.size
        drawText(
            textLayoutResult,
            topLeft = Offset(x - textSize.width / 2, y + 20),
        )
        angleInDegrees = 240.0

        x = -(radius * kotlin.math.sin(Math.toRadians(angleInDegrees))).toFloat() + (size.width / 2)
        y = (radius * kotlin.math.cos(Math.toRadians(angleInDegrees))).toFloat() + (size.width / 2)
        val center2 = Offset(x, y)
        textLayoutResult = textMeasurer.measure(
            text = AnnotatedString(sunset),
            style = TextStyle(fontSize = 12.sp, color = textColor),
        )
        textSize = textLayoutResult.size
        drawText(
            textLayoutResult,
            topLeft = Offset(x - textSize.width / 2, y + 20),
        )

        drawLine(
            strokeWidth = 16f,
            start = center1.copy(center1.x - 50, center1.y + 6),
            end = center2.copy(center2.x + 50, center2.y + 6),
            color = Color.White,
        )
    }
}
