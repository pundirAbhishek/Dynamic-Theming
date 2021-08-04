package com.example.dynamictheming.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val DynamicThemingShapes = Shapes(
    small = RoundedCornerShape(percent = 50),
    medium = RoundedCornerShape(size = 8.dp),
    large = RoundedCornerShape(size = 0.dp)
)
