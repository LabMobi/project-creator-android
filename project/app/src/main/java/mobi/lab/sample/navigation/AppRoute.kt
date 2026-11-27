package mobi.lab.sample.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Type-safe Navigation 3 destinations for the application.
 *
 * All routes are [@Serializable] and implement [NavKey] so the back stack
 * managed by [androidx.navigation3.runtime.rememberNavBackStack] automatically
 * survives configuration changes and process death.
 *
 * Routes require authentication by default ([requiresAuth] = `true`).
 * Explicitly override [requiresAuth] to `false` on unauthenticated routes.
 */
@Serializable
sealed interface AppRoute : NavKey {

    val requiresAuth: Boolean
        get() = true

    @Serializable
    data object Login : AppRoute {
        override val requiresAuth: Boolean
            get() = false
    }

    @Serializable
    data object Main : AppRoute

    @Serializable
    data object Debug : AppRoute
}
