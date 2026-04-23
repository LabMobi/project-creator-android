@file:Suppress("MagicNumber")

package mobi.lab.sample.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mobi.lab.components.compose.theme.LabTheme
import mobi.lab.components.compose.theme.LabThemeDefaults
import mobi.lab.components.compose.util.withAlpha
import mobi.lab.components.compose.widget.button.LabButton
import mobi.lab.components.compose.widget.button.LabTonedButton

// Primary - Blue
private val Blue10 = Color(0xFF001F29)
private val Blue20 = Color(0xFF003544)
private val Blue30 = Color(0xFF004D60)
private val Blue40 = Color(0xFF006782)
private val Blue80 = Color(0xFF5BD5FA)
private val Blue90 = Color(0xFFBBE9FF)
private val Blue95 = Color(0xFFDFF5FF)

// Secondary - Grey/Blue
private val GreyBlue10 = Color(0xFF061E28)
private val GreyBlue20 = Color(0xFF1E333D)
private val GreyBlue30 = Color(0xFF344A55)
private val GreyBlue40 = Color(0xFF4B626D)
private val GreyBlue80 = Color(0xFFB2CAD6)
private val GreyBlue90 = Color(0xFFCEE7F3)
private val GreyBlue95 = Color(0xFFEAF5FA)

// Tertiary - Purple
private val Purple10 = Color(0xFF181837)
private val Purple20 = Color(0xFF2E2F4D)
private val Purple40 = Color(0xFF5B5B7E)
private val Purple80 = Color(0xFFC4C3EA)
private val Purple90 = Color(0xFFE1DFFF)

// Error - Red
private val Red10 = Color(0xFF410002)
private val Red20 = Color(0xFF690005)
private val Red30 = Color(0xFF93000A)
private val Red40 = Color(0xFFBA1A1A)
private val Red80 = Color(0xFFFFB4AB)
private val Red90 = Color(0xFFFFDAD6)
private val Red95 = Color(0xFFFFEDE9)

// Success - Green
private val Green10 = Color(0xFF002104)
private val Green20 = Color(0xFF00390B)
private val Green30 = Color(0xFF005313)
private val Green40 = Color(0xFF006E1C)
private val Green80 = Color(0xFF88D982)
private val Green90 = Color(0xFFA3F59C)
private val Green95 = Color(0xFFE1FFD9)

// Caution - Orange
private val Orange10 = Color(0xFF2C1600)
private val Orange20 = Color(0xFF482700)
private val Orange30 = Color(0xFF673D00)
private val Orange40 = Color(0xFF895100)
private val Orange80 = Color(0xFFFFB85F)
private val Orange90 = Color(0xFFFFDDB1)
private val Orange95 = Color(0xFFFFEEE0)

// Neutral - Grey
private val Grey10 = Color(0xFF191C1D)
private val Grey30 = Color(0xFF444748)
private val Grey90 = Color(0xFFE1E2E4)
private val Grey99 = Color(0xFFFBFCFE)

// Neutral Variant - Grey/Blue
private val GreyBlueVar30 = Color(0xFF40484C)
private val GreyBlueVar50 = Color(0xFF70787D)
private val GreyBlueVar80 = Color(0xFFBFC8CC)
private val GreyBlueVar90 = Color(0xFFDBE4E8)

private const val EMPHASIS_VERY_HIGH = 0.64f

private val LightColorScheme = lightColorScheme(
    primary = Blue40,
    onPrimary = Color.White,
    primaryContainer = Blue90,
    onPrimaryContainer = Blue10,
    secondary = GreyBlue40,
    onSecondary = Color.White,
    secondaryContainer = GreyBlue90,
    onSecondaryContainer = GreyBlue10,
    tertiary = Purple40,
    onTertiary = Color.White,
    tertiaryContainer = Purple90,
    onTertiaryContainer = Purple10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = Grey99,
    onSurface = Grey10,
    surfaceVariant = GreyBlueVar90,
    onSurfaceVariant = GreyBlueVar30,
    outline = GreyBlueVar50,
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    onPrimary = Blue10,
    primaryContainer = Blue20,
    onPrimaryContainer = Blue90,
    secondary = GreyBlue80,
    onSecondary = GreyBlue10,
    secondaryContainer = GreyBlue20,
    onSecondaryContainer = GreyBlue90,
    tertiary = Purple80,
    onTertiary = Purple10,
    tertiaryContainer = Purple20,
    onTertiaryContainer = Purple90,
    error = Red80,
    onError = Red10,
    errorContainer = Red20,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    surface = Grey10,
    onSurface = Grey90,
    surfaceVariant = GreyBlueVar30,
    onSurfaceVariant = GreyBlueVar80,
    outline = GreyBlueVar50,
)

@Composable
private fun lightLabColors() = LabThemeDefaults.lightColors().copy(
    primary = Blue40,
    primaryFocused = Blue30,
    primaryPressed = Blue30,
    onPrimary = Color.White,
    primarySurface = Blue95,
    primarySurfaceFocused = Blue95.withAlpha(EMPHASIS_VERY_HIGH),
    primarySurfacePressed = Blue95.withAlpha(EMPHASIS_VERY_HIGH),
    onPrimarySurface = Blue10,
    secondary = GreyBlue40,
    secondaryFocused = GreyBlue30,
    secondaryPressed = GreyBlue30,
    onSecondary = Color.White,
    secondarySurface = GreyBlue95,
    secondarySurfaceFocused = GreyBlue95.withAlpha(EMPHASIS_VERY_HIGH),
    secondarySurfacePressed = GreyBlue95.withAlpha(EMPHASIS_VERY_HIGH),
    onSecondarySurface = GreyBlue10,
    error = Red40,
    errorFocused = Red30,
    errorPressed = Red30,
    onError = Color.White,
    errorSurface = Red95,
    errorSurfaceFocused = Red95.withAlpha(EMPHASIS_VERY_HIGH),
    errorSurfacePressed = Red95.withAlpha(EMPHASIS_VERY_HIGH),
    onErrorSurface = Red10,
    success = Green40,
    successFocused = Green30,
    successPressed = Green30,
    onSuccess = Color.White,
    successSurface = Green95,
    successSurfaceFocused = Green95.withAlpha(EMPHASIS_VERY_HIGH),
    successSurfacePressed = Green95.withAlpha(EMPHASIS_VERY_HIGH),
    onSuccessSurface = Green10,
    caution = Orange40,
    cautionFocused = Orange30,
    cautionPressed = Orange30,
    onCaution = Color.White,
    cautionSurface = Orange95,
    cautionSurfaceFocused = Orange95.withAlpha(EMPHASIS_VERY_HIGH),
    cautionSurfacePressed = Orange95.withAlpha(EMPHASIS_VERY_HIGH),
    onCautionSurface = Orange10,
    background = Grey99,
    onBackground = Grey10,
    surface = Grey99,
    onSurface = Grey10,
    outline = GreyBlueVar50,
    divider = Grey90,
    surfaceVariant = GreyBlueVar90,
    onSurfaceVariant = GreyBlueVar30,
)

@Composable
private fun darkLabColors() = LabThemeDefaults.darkColors().copy(
    primary = Blue80,
    primaryFocused = Blue90,
    primaryPressed = Blue90,
    onPrimary = Blue10,
    primarySurface = Blue20,
    primarySurfaceFocused = Blue20.withAlpha(EMPHASIS_VERY_HIGH),
    primarySurfacePressed = Blue20.withAlpha(EMPHASIS_VERY_HIGH),
    onPrimarySurface = Blue90,
    secondary = GreyBlue80,
    secondaryFocused = GreyBlue90,
    secondaryPressed = GreyBlue90,
    onSecondary = GreyBlue10,
    secondarySurface = GreyBlue20,
    secondarySurfaceFocused = GreyBlue20.withAlpha(EMPHASIS_VERY_HIGH),
    secondarySurfacePressed = GreyBlue20.withAlpha(EMPHASIS_VERY_HIGH),
    onSecondarySurface = GreyBlue90,
    error = Red80,
    errorFocused = Red90,
    errorPressed = Red90,
    onError = Red10,
    errorSurface = Red20,
    errorSurfaceFocused = Red20.withAlpha(EMPHASIS_VERY_HIGH),
    errorSurfacePressed = Red20.withAlpha(EMPHASIS_VERY_HIGH),
    onErrorSurface = Red90,
    success = Green80,
    successFocused = Green90,
    successPressed = Green90,
    onSuccess = Green10,
    successSurface = Green20,
    successSurfaceFocused = Green20.withAlpha(EMPHASIS_VERY_HIGH),
    successSurfacePressed = Green20.withAlpha(EMPHASIS_VERY_HIGH),
    onSuccessSurface = Green90,
    caution = Orange80,
    cautionFocused = Orange90,
    cautionPressed = Orange90,
    onCaution = Orange10,
    cautionSurface = Orange20,
    cautionSurfaceFocused = Orange20.withAlpha(EMPHASIS_VERY_HIGH),
    cautionSurfacePressed = Orange20.withAlpha(EMPHASIS_VERY_HIGH),
    onCautionSurface = Orange90,
    background = Grey10,
    onBackground = Grey90,
    surface = Grey10,
    onSurface = Grey90,
    outline = GreyBlueVar50,
    divider = Grey30,
    surfaceVariant = GreyBlueVar30,
    onSurfaceVariant = GreyBlueVar80,
)

@Composable
fun AppTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val labColors = if (isDark) darkLabColors() else lightLabColors()
    val materialColors = if (isDark) DarkColorScheme else LightColorScheme

    LabTheme(
        isDark = isDark,
        colors = labColors,
        typography = LabThemeDefaults.typography(fontFamily = FontFamily.Default),
        materialColorScheme = materialColors,
        materialTypography = Typography(),
        content = content,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewAppTheme() {
    AppTheme(isDark = false) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Primary Button", style = MaterialTheme.typography.titleMedium)
            LabButton(onClick = {}, text = "Primary Button")

            Text("Secondary Button", style = MaterialTheme.typography.titleMedium)
            LabTonedButton(onClick = {}, text = "Secondary Button")

            Text("Success Color", style = MaterialTheme.typography.titleMedium)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(LabTheme.colors.success)
            )
        }
    }
}
