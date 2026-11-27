package mobi.lab.sample.common.platform

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import mobi.lab.sample.domain.usecases.auth.DeleteSessionUseCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LogoutMonitorTest {

    @Mock private lateinit var deleteSessionUseCase: DeleteSessionUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        LogoutMonitor.init(deleteSessionUseCase)
        LogoutMonitor.reset()
    }

    @After
    fun tearDown() {
        LogoutMonitor.reset()
    }

    @Test
    fun logout_deletes_session_and_retains_pending_logout_until_consumed() {
        LogoutMonitor.logout()

        verify(deleteSessionUseCase, times(1)).execute()
        assertTrue(LogoutMonitor.logoutPending.value)
        assertTrue(LogoutMonitor.consumePendingLogout())
        assertFalse(LogoutMonitor.logoutPending.value)
        assertFalse(LogoutMonitor.consumePendingLogout())
    }

    @Test
    fun duplicate_logout_calls_before_reset_are_ignored() {
        LogoutMonitor.logout()
        assertTrue(LogoutMonitor.consumePendingLogout())

        LogoutMonitor.logout()

        verify(deleteSessionUseCase, times(1)).execute()
        assertFalse(LogoutMonitor.logoutPending.value)
        assertFalse(LogoutMonitor.consumePendingLogout())
    }

    @Test
    fun reset_clears_pending_logout_and_re_enables_future_logouts() {
        LogoutMonitor.logout()
        assertTrue(LogoutMonitor.logoutPending.value)

        LogoutMonitor.reset()

        assertFalse(LogoutMonitor.logoutPending.value)
        assertFalse(LogoutMonitor.consumePendingLogout())

        LogoutMonitor.logout()

        verify(deleteSessionUseCase, times(2)).execute()
        assertTrue(LogoutMonitor.logoutPending.value)
    }
}
