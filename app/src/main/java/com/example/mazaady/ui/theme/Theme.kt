package com.example.mazaady.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun MazaadyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
}
data class LaunchesColorScheme(
    val background: Color,
    val surface: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val topBar: Color,
    val onTopBar: Color
)
@Composable
fun launchesColorScheme(darkTheme: Boolean = isSystemInDarkTheme()): LaunchesColorScheme {
    return if (darkTheme) {
        LaunchesColorScheme(
            background = AppColors.DarkBackground,
            surface = AppColors.DarkSurface,
            onSurface = AppColors.DarkOnSurface,
            onSurfaceVariant = AppColors.DarkOnSurfaceVariant,
            topBar = AppColors.DarkTopBar,
            onTopBar = AppColors.White
        )
    } else {
        LaunchesColorScheme(
            background = AppColors.LightBackground,
            surface = AppColors.LightSurface,
            onSurface = AppColors.LightOnSurface,
            onSurfaceVariant = AppColors.LightOnSurfaceVariant,
            topBar = AppColors.LightTopBar,
            onTopBar = AppColors.Black
        )
    }
}