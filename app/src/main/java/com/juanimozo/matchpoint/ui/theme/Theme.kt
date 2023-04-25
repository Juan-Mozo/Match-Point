package com.juanimozo.matchpoint.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class MatchPalette(
    val material: Colors
) {
    val primary: Color get() = material.primary
    val primaryVariant: Color get() = material.primaryVariant
    val secondary: Color get() = material.secondary
    val secondaryVariant: Color get() = material.secondaryVariant
    val background: Color get() = material.background
    val surface: Color get() = material.surface
    val error: Color get() = material.error
    val onPrimary: Color get() = material.onPrimary
    val onSecondary: Color get() = material.onSecondary
    val onBackground: Color get() = material.onBackground
    val onSurface: Color get() = material.onSurface
    val onError: Color get() = material.onError
    val isLight: Boolean get() = material.isLight
}

private val LightColorPalette = MatchPalette(
    material = lightColors(
        primary = NavyBlue,
        primaryVariant = NavyBlue,
        secondary = Green,
        secondaryVariant = Green,
        background = Color.White,
        onBackground = NavyBlue,
        onPrimary = Beige
    )
)

private val DarkColorPalette = MatchPalette(
    material = darkColors(
        primary = NavyBlue,
        primaryVariant = NavyBlue,
        secondary = Green,
        secondaryVariant = Green,
        background = Color.White,
        onBackground = NavyBlue,
        onPrimary = Beige
    )
)

private val LocalColors = staticCompositionLocalOf { LightColorPalette }

@Composable
fun MatchPointTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) { DarkColorPalette } else { LightColorPalette }
    val typography = if (darkTheme) { DarkTypography } else { LightTypography }

    CompositionLocalProvider(LocalColors provides colors) {
        MaterialTheme(
            colors = colors.material,
            typography = typography,
            shapes = Shapes,
            content = content
        )
    }
}

val MaterialTheme.matchPalette: MatchPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current