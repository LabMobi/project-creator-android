package mobi.lab.sample.common.debug

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import mobi.lab.sample.navigation.Navigator

/**
 * Registers build-variant-specific debug destinations into the Navigation 3 [EntryProviderScope].
 *
 * Implemented in `src/debug` to provide the debug menu destination and as a no-op in `src/release`
 * so release builds do not pull in debug UI or logging tools.
 */
fun interface DebugNavigation {
    fun register(scope: EntryProviderScope<NavKey>, navigator: Navigator)
}
