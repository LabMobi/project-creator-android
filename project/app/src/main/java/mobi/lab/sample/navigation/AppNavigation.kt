package mobi.lab.sample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import mobi.lab.sample.common.debug.DebugNavigation
import mobi.lab.sample.common.platform.LogoutMonitor
import mobi.lab.sample.demo.login.loginEntry
import mobi.lab.sample.demo.main.mainEntry
import mobi.lab.sample.demo.prototype.prototypeEntry

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

    LaunchedEffect(navigator) {
        LogoutMonitor.logoutPending.collect { isPending ->
            if (isPending && LogoutMonitor.consumePendingLogout()) {
                navigator.replaceAll(AppRoute.Login)
            }
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = { navigator.goBack() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            loginEntry(navigator)
            mainEntry(navigator)
            prototypeEntry(navigator)
            debugNavigation.register(this, navigator)
        },
    )
}
