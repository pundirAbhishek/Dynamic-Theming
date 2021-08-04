package com.example.dynamictheming.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun DynamicThemingTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = DynamicThemingColors,
        typography = DynamicThemingTypography,
        shapes = DynamicThemingShapes,
        content = content
    )
}
