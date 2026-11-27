package mobi.lab.sample

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import mobi.lab.sample.common.debug.DebugNavigation
import mobi.lab.sample.common.ui.AppTheme
import mobi.lab.sample.navigation.AppNavigation
import mobi.lab.sample.navigation.AppRoute
import mobi.lab.sample.splash.SplashViewModel
import javax.inject.Inject

/**
 * Single Activity hosting the entire Compose UI and Navigation 3 back stack.
 *
 * Extends [AppCompatActivity] (a [androidx.fragment.app.FragmentActivity]) so the template's
 * [androidx.fragment.app.DialogFragment] examples continue to work via `supportFragmentManager`.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var debugNavigation: DebugNavigation

    private val splashViewModel: SplashViewModel by viewModels()

    private var startDestination by mutableStateOf<AppRoute?>(null)
    private var freshAuthState by mutableStateOf<Boolean?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        /**
         * Android launcher bug workaround. Previous task is not resumed, but a new one is opened.
         * See:
         * https://issuetracker.google.com/issues/64108432 A new bug report
         * https://issuetracker.google.com/issues/36907463 Old, closed bug report
         */
        if (!isTaskRoot) {
            val intent = intent
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && TextUtils.equals(Intent.ACTION_MAIN, intent.action)) {
                finish()
                return
            }
        }

        splashScreen.setKeepOnScreenCondition { startDestination == null }

        splashViewModel.action.observe(this) { event ->
            if (startDestination == null) {
                val freshAction = event.getContentIfNotHandled()
                if (freshAction != null) {
                    freshAuthState = freshAction == SplashViewModel.Action.LaunchApplication
                }
                startDestination = when (event.peekContent()) {
                    SplashViewModel.Action.LaunchApplication -> AppRoute.Main
                    SplashViewModel.Action.LaunchLogin -> AppRoute.Login
                }
            }
        }

        setContent {
            AppTheme {
                startDestination?.let { initialRoute ->
                    AppNavigation(
                        startDestination = initialRoute,
                        freshAuthState = freshAuthState,
                        debugNavigation = debugNavigation,
                    )
                }
            }
        }
    }
}
