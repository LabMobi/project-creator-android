package mobi.lab.sample.debug

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import mobi.lab.sample.common.debug.DebugNavigation
import mobi.lab.sample.navigation.Navigator

class ReleaseDebugNavigation : DebugNavigation {
    override fun register(scope: EntryProviderScope<NavKey>, navigator: Navigator) {
        // No-op in release builds
    }
}
