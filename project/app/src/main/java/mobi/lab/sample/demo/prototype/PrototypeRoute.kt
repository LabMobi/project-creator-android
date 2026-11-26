package mobi.lab.sample.demo.prototype

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import mobi.lab.sample.common.ui.ObserveSingleEvent
import mobi.lab.sample.common.util.NavUtil
import mobi.lab.sample.navigation.AppRoute
import mobi.lab.sample.navigation.Navigator

/**
 * Demonstrates passing type-safe Navigation 3 route arguments into a Hilt ViewModel
 * using `@AssistedInject` and `hiltViewModel`'s `creationCallback`.
 */
fun EntryProviderScope<NavKey>.prototypeEntry(navigator: Navigator) {
    entry<AppRoute.Prototype> { route ->
        PrototypeRoute(
            url = route.url,
            onClose = { navigator.goBack() },
        )
    }
}

@Composable
fun PrototypeRoute(
    url: String,
    onClose: () -> Unit,
    viewModel: PrototypeViewModel = hiltViewModel<PrototypeViewModel, PrototypeViewModel.Factory>(
        creationCallback = { factory -> factory.create(url) },
    ),
) {
    val context = LocalContext.current

    ObserveSingleEvent(viewModel.action) { action ->
        when (action) {
            is PrototypeViewModel.Action.OpenWebLinkAndClose -> {
                NavUtil.openBrowser(context, action.url)
                onClose()
            }
            PrototypeViewModel.Action.Close -> {
                onClose()
            }
        }
    }
}
