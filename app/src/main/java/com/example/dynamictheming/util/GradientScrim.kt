package com.example.dynamictheming.util

import androidx.annotation.FloatRange
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.pow

/**
 * Draws a vertical gradient scrim in the foreground.
 *
 * @param color The color of the gradient scrim.
 * @param startYPercentage The start y value, in percentage of the layout's height (0f to 1f)
 * @param endYPercentage The end y value, in percentage of the layout's height (0f to 1f)
 */

fun Modifier.verticalGradientScrim(
    color: Color,
    @FloatRange(from = 0.0, to = 1.0) startYPercentage: Float = 0f,
    @FloatRange(from = 0.0, to = 1.0) endYPercentage: Float = 1f
): Modifier = composed {
    val numStops = 16
    val colors = remember(color, numStops) {
        listOf(color.copy(alpha = 0f), color)
    }

    var height by remember { mutableStateOf(0f) }
    val brush = remember(color, numStops, startYPercentage, endYPercentage, height) {
        Brush.verticalGradient(
            colors = colors,
            startY = height * startYPercentage,
            endY = height * endYPercentage
        )
    }
    drawBehind {
        height = size.height
        drawRect(brush = brush)
    }
}
