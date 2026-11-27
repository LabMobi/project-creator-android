package mobi.lab.sample.navigation

import androidx.navigation3.runtime.NavKey
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NavigatorTest {

    @Test
    fun navigate_to_pushes_destination_onto_back_stack() {
        val backStack = mutableListOf<NavKey>(AppRoute.Main)
        val navigator = Navigator(backStack)

        navigator.navigateTo(AppRoute.Prototype("https://lab.mobi"))

        assertEquals(listOf<NavKey>(AppRoute.Main, AppRoute.Prototype("https://lab.mobi")), backStack)
        assertEquals(AppRoute.Prototype("https://lab.mobi"), navigator.currentDestination)
    }

    @Test
    fun go_back_pops_top_destination_when_stack_has_multiple_entries() {
        val backStack = mutableListOf<NavKey>(AppRoute.Main, AppRoute.Debug)
        val navigator = Navigator(backStack)

        val popped = navigator.goBack()

        assertTrue(popped)
        assertEquals(listOf<NavKey>(AppRoute.Main), backStack)
        assertEquals(AppRoute.Main, navigator.currentDestination)
    }

    @Test
    fun go_back_does_not_pop_root_destination() {
        val backStack = mutableListOf<NavKey>(AppRoute.Main)
        val navigator = Navigator(backStack)

        val popped = navigator.goBack()

        assertFalse(popped)
        assertEquals(listOf<NavKey>(AppRoute.Main), backStack)
    }

    @Test
    fun replace_all_clears_existing_stack_and_sets_new_root() {
        val backStack = mutableListOf<NavKey>(AppRoute.Main, AppRoute.Debug)
        val navigator = Navigator(backStack)

        navigator.replaceAll(AppRoute.Login)

        assertEquals(listOf<NavKey>(AppRoute.Login), backStack)
        assertEquals(AppRoute.Login, navigator.currentDestination)
    }

    @Test
    fun replace_all_with_same_single_root_keeps_stack_unchanged() {
        val backStack = mutableListOf<NavKey>(AppRoute.Login)
        val navigator = Navigator(backStack)

        navigator.replaceAll(AppRoute.Login)

        assertEquals(listOf<NavKey>(AppRoute.Login), backStack)
    }

    @Test
    fun reconcile_auth_replaces_protected_stack_with_login_when_unauthenticated() {
        val backStack = mutableListOf<NavKey>(AppRoute.Main, AppRoute.Debug)
        val navigator = Navigator(backStack)

        navigator.reconcileAuth(isAuthenticated = false)

        assertEquals(listOf<NavKey>(AppRoute.Login), backStack)
    }

    @Test
    fun reconcile_auth_preserves_unauthenticated_stack_when_unauthenticated() {
        val backStack = mutableListOf<NavKey>(AppRoute.Login)
        val navigator = Navigator(backStack)

        navigator.reconcileAuth(isAuthenticated = false)

        assertEquals(listOf<NavKey>(AppRoute.Login), backStack)
    }

    @Test
    fun reconcile_auth_preserves_authenticated_stack_when_authenticated() {
        val backStack = mutableListOf<NavKey>(AppRoute.Main, AppRoute.Debug)
        val navigator = Navigator(backStack)

        navigator.reconcileAuth(isAuthenticated = true)

        assertEquals(listOf<NavKey>(AppRoute.Main, AppRoute.Debug), backStack)
    }

    @Test
    fun reconcile_auth_treats_unknown_nav_keys_as_protected() {
        val unknownKey = object : NavKey {}
        val backStack = mutableListOf<NavKey>(unknownKey)
        val navigator = Navigator(backStack)

        navigator.reconcileAuth(isAuthenticated = false)

        assertEquals(listOf<NavKey>(AppRoute.Login), backStack)
    }
}
