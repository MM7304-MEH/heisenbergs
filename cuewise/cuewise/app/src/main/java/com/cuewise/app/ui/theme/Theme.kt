package com.cuewise.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Coral,
    onPrimary = Color.White,
    secondary = MintDeep,
    onSecondary = Midnight,
    tertiary = LavenderDeep,
    background = Paper,
    onBackground = Ink,
    surface = Color.White,
    onSurface = Ink,
    surfaceVariant = Color(0xFFF0F1F6),
    onSurfaceVariant = Muted,
    outline = Line
)

private val DarkColors = darkColorScheme(
    primary = Coral,
    onPrimary = Midnight,
    secondary = Mint,
    onSecondary = Midnight,
    tertiary = Lavender,
    background = Midnight,
    onBackground = Color(0xFFF4F4FA),
    surface = MidnightSoft,
    onSurface = Color(0xFFF4F4FA),
    surfaceVariant = Color(0xFF222A43),
    onSurfaceVariant = Color(0xFFC3C8D7),
    outline = Color(0xFF3A4564)
)

@Composable
fun CuewiseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = CuewiseTypography,
        content = content
    )
}
