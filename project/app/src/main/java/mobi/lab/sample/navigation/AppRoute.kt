package mobi.lab.sample.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Type-safe Navigation 3 destinations for the application.
 *
 * All routes are [@Serializable] and implement [NavKey] so the back stack
 * managed by [androidx.navigation3.runtime.rememberNavBackStack] automatically
 * survives configuration changes and process death.
 */
@Serializable
sealed interface AppRoute : NavKey {

    @Serializable
    data object Login : AppRoute

    @Serializable
    data object Main : AppRoute

    @Serializable
    data class Prototype(val url: String) : AppRoute

    @Serializable
    data object Debug : AppRoute
}
