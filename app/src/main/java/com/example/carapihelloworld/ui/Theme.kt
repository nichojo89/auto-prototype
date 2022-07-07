package com.example.carapihelloworld.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    surface = OccDarkBlue,
    onSurface = OccBlue,
    primary = OccBlue,
    onPrimary = Chartreuse
)

private val LightColorPalette = lightColors(
    surface = OccDarkBlue,
    onSurface = Color.White,
    primary = OccLightBlue,
    onPrimary = OccBlue,
    background = OvalTwo
)

@Composable
fun NavistarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}