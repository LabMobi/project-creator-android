package mobi.lab.sample.demo.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import mobi.lab.sample.common.platform.LogoutMonitor
import mobi.lab.sample.common.ui.ObserveSingleEvent
import mobi.lab.sample.common.util.NavUtil
import mobi.lab.sample.navigation.AppRoute
import mobi.lab.sample.navigation.Navigator

fun EntryProviderScope<NavKey>.mainEntry(navigator: Navigator) {
    entry<AppRoute.Main> {
        MainRoute(
            onOpenDebug = { navigator.navigateTo(AppRoute.Debug) },
            onLogout = { navigator.replaceAll(AppRoute.Login) },
        )
    }
}

@Composable
fun MainRoute(
    onOpenDebug: () -> Unit,
    onLogout: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        // Reset logout monitor: if we can see this screen, we have a valid session.
        LogoutMonitor.reset()
    }

    ObserveSingleEvent(viewModel.action) { action ->
        when (action) {
            is MainViewModel.Action.OpenWebLink -> NavUtil.openBrowser(context, action.url)
            MainViewModel.Action.RestartApplication -> onLogout()
            MainViewModel.Action.OpenDebug -> onOpenDebug()
        }
    }

    MainScreen(
        onOpenPrototypeClicked = viewModel::onOpenPrototypeClicked,
        onLogoutClicked = viewModel::onLogoutClicked,
        onDebugClicked = viewModel::onDebugClicked,
    )
}
