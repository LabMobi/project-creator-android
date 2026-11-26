package mobi.lab.sample.navigation

import androidx.navigation3.runtime.NavKey

/**
 * Thin navigation controller wrapping a Navigation 3 back stack.
 *
 * Accepting [MutableList] keeps navigation logic independent of Compose internals
 * and straightforward to unit test.
 */
class Navigator(private val backStack: MutableList<NavKey>) {

    val currentDestination: NavKey?
        get() = backStack.lastOrNull()

    fun navigateTo(route: NavKey) {
        backStack.add(route)
    }

    fun goBack(): Boolean {
        if (backStack.size <= 1) {
            return false
        }
        backStack.removeAt(backStack.lastIndex)
        return true
    }

    fun replaceAll(route: NavKey) {
        if (backStack.size == 1 && backStack.first() == route) {
            return
        }
        backStack.clear()
        backStack.add(route)
    }
}
