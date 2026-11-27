package mobi.lab.sample.common.platform

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import mobi.lab.sample.domain.usecases.auth.DeleteSessionUseCase
import timber.log.Timber

/**
 * Helps to handle automatic logouts. The automatic logout is locked until we are sure that we have logged back in.
 * [logout] is called whenever a network request fails because of an unauthorized session.
 * Needs to be a singleton (object) to enforce global syncing of logout requests.
 *
 * Pending logout state is retained in [logoutPending] until the navigation host acknowledges it via
 * [consumePendingLogout], preventing lost logout resets when no collector is active (e.g. during
 * Activity recreation).
 *
 * [reset] is called once the user reaches an authenticated destination (e.g. `MainRoute`),
 * confirming we have a valid session and are ready to handle future unauthorized errors.
 */
object LogoutMonitor {

    @Volatile private var logoutHandled = false
    @Volatile private lateinit var deleteSessionUseCase: DeleteSessionUseCase

    private val _logoutPending = MutableStateFlow(false)
    val logoutPending: StateFlow<Boolean> = _logoutPending.asStateFlow()

    fun init(deleteSessionUseCase: DeleteSessionUseCase) {
        this.deleteSessionUseCase = deleteSessionUseCase
    }

    /**
     * Call this once the application knows that the user is in a logged in state
     * and can start interacting with the application. The main starting screen for example.
     */
    fun reset() {
        synchronized(this) {
            logoutHandled = false
            _logoutPending.value = false
        }
    }

    /**
     * Call this once the application knows that the session is not valid.
     */
    fun logout() {
        synchronized(this) {
            if (logoutHandled) {
                Timber.e("logout called, already called, ignore")
                return
            }

            logoutHandled = true
            deleteSessionUseCase.execute()
            _logoutPending.value = true
        }
    }

    /**
     * Acknowledges and clears a pending logout event once navigation has handled it.
     *
     * @return `true` if a logout was pending and has now been consumed, `false` otherwise.
     */
    fun consumePendingLogout(): Boolean = synchronized(this) {
        if (!_logoutPending.value) {
            return@synchronized false
        }
        _logoutPending.value = false
        true
    }
}
