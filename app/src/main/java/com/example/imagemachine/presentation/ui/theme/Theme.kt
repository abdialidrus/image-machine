package com.example.imagemachine.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colorPalette = lightColors(
    primary = RedOrange,
    background = Color.White,
    onBackground = DarkGray,
    surface = LightBlue,
    onSurface = DarkGray
)

@Composable
fun ImageMachineTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = colorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}