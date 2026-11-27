package mobi.lab.sample.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import mobi.lab.sample.common.debug.DebugNavigation
import mobi.lab.sample.common.platform.LogoutMonitor
import mobi.lab.sample.demo.login.loginEntry
import mobi.lab.sample.demo.main.mainEntry

private const val TRANSITION_DURATION_MILLIS = 300

@Composable
fun AppNavigation(
    startDestination: AppRoute,
    freshAuthState: Boolean?,
    debugNavigation: DebugNavigation,
) {
    val backStack = rememberNavBackStack(startDestination)
    val navigator = remember(backStack) {
        Navigator(backStack).apply {
            if (freshAuthState != null) {
                reconcileAuth(freshAuthState)
            }
            if (LogoutMonitor.consumePendingLogout()) {
                replaceAll(AppRoute.Login)
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(navigator, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            LogoutMonitor.logoutPending.collect { isPending ->
                if (isPending && LogoutMonitor.consumePendingLogout()) {
                    navigator.replaceAll(AppRoute.Login)
                }
            }
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = { navigator.goBack() },
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(TRANSITION_DURATION_MILLIS),
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(TRANSITION_DURATION_MILLIS),
            )
        },
        popTransitionSpec = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(TRANSITION_DURATION_MILLIS),
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(TRANSITION_DURATION_MILLIS),
            )
        },
        predictivePopTransitionSpec = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(TRANSITION_DURATION_MILLIS),
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(TRANSITION_DURATION_MILLIS),
            )
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            loginEntry(navigator)
            mainEntry(navigator)
            debugNavigation.register(this, navigator)
        },
    )
}
