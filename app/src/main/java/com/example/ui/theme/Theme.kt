package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlueLight,
    onPrimary = NavyDark,
    primaryContainer = PrimaryBlueDark,
    onPrimaryContainer = PrimaryBlueLight,
    secondary = SafetyAmberLight,
    onSecondary = NavyDark,
    secondaryContainer = SafetyAmber,
    onSecondaryContainer = SafetyAmberContainer,
    background = NeutralDarkBg,
    onBackground = NeutralDarkText,
    surface = NeutralDarkCard,
    onSurface = NeutralDarkText,
    surfaceVariant = NavyElevated,
    onSurfaceVariant = NeutralDarkSubtext,
    outline = NeutralDarkCardBorder
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = NeutralLightCard,
    primaryContainer = Color(0xFFDBEAFE),
    onPrimaryContainer = PrimaryBlueDark,
    secondary = SafetyAmber,
    onSecondary = NeutralLightCard,
    secondaryContainer = SafetyAmberContainer,
    onSecondaryContainer = OnSafetyAmberContainer,
    background = NeutralLightBg,
    onBackground = NeutralLightText,
    surface = NeutralLightCard,
    onSurface = NeutralLightText,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = NeutralLightSubtext,
    outline = NeutralLightCardBorder
)

@Composable
fun CDLPrepTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep intentional brand colors for commercial driving prep
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
