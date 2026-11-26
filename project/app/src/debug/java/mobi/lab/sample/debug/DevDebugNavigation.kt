package mobi.lab.sample.debug

import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import mobi.lab.sample.common.debug.DebugNavigation
import mobi.lab.sample.common.platform.LogoutMonitor
import mobi.lab.sample.navigation.AppRoute
import mobi.lab.sample.navigation.Navigator
import mobi.lab.scrolls.LogImplFile
import mobi.lab.scrolls.LogViewBuilder

class DevDebugNavigation : DebugNavigation {
    override fun register(scope: EntryProviderScope<NavKey>, navigator: Navigator) {
        scope.entry<AppRoute.Debug> {
            val context = LocalContext.current
            DebugScreen(
                onLogoutClicked = { LogoutMonitor.logout() },
                onCrashClicked = { error("Test crash triggered from DebugScreen") },
                onScrollsClicked = {
                    LogViewBuilder()
                        .setDirectory(LogImplFile.getLogDir())
                        .addTags("manual_post")
                        .launchActivity(context)
                },
            )
        }
    }
}
