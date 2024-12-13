package com.example.tourism_project.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Warna Utama
private val LightColors = lightColorScheme(
    primary = Color(0xFF1B5E20), // Hijau untuk alam
    onPrimary = Color.White,
    secondary = Color(0xFFFF9800), // Oranye untuk pantai
    onSecondary = Color.White,
    background = Color(0xFFF5F5F5), // Latar belakang terang
    onBackground = Color.Black
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color.Black,
    secondary = Color(0xFFFFB74D),
    onSecondary = Color.Black,
    background = Color(0xFF303030),
    onBackground = Color.White
)

// Tipografi
val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.Black
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.Gray
    )
)

// Bentuk Komponen
val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)
)

@Composable
fun Tourism_ProjectTheme(
    darkTheme: Boolean = false, // Berubah sesuai pengaturan sistem
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
