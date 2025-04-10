package mobi.lab.sample.common.util

import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

object EdgeToEdgeUtil {
    fun setAllInsetsForBarsAndCutout(targetView: View) {
        ViewCompat.setOnApplyWindowInsetsListener(targetView) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                    or WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
                bottom = bars.bottom,
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    fun setLeftTopRightInsetsForBarsAndCutout(targetView: View) {
        ViewCompat.setOnApplyWindowInsetsListener(targetView) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                    or WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    fun setLightStatusBarIcons(window: Window) {
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false
    }
}
