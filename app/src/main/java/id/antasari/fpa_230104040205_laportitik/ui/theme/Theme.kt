package id.antasari.fpa_230104040205_laportitik.ui.theme

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Skema Warna Terang (Aplikasi ini dominan Putih/Oranye)
private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,
    secondary = OrangeLight,
    tertiary = RedEmergency,
    background = GreyBackground,
    surface = WhiteSurface,
    onPrimary = WhiteSurface,
    onSecondary = WhiteSurface,
    onTertiary = WhiteSurface,
    onBackground = BlackText,
    onSurface = BlackText
)

// Skema Gelap (Optional, kita samakan dulu biar konsisten dengan mockup)
private val DarkColorScheme = darkColorScheme(
    primary = OrangePrimary,
    secondary = OrangeLight,
    tertiary = RedEmergency
)

@Composable
fun LaporTitikTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Kita set FALSE agar warna Oranye kita tidak tertimpa warna wallpaper HP
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
            val window = (view.context as Activity).window
            // Status bar warna Oranye
            window.statusBarColor = OrangePrimary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}