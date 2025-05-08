package mobi.lab.sample.di

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import mobi.lab.sample.common.rx.SchedulerProvider
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertSame

/**
 * A sample test showing how to inject dependencies via Dagger.
 * Verifies that our TestAppComponent gets its schedulers from TestSchedulerModule
 */
@HiltAndroidTest
class SchedulerModuleTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var schedulers: SchedulerProvider

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun verify_test_schedulers() {
        assertSame(schedulers.main, AndroidSchedulers.mainThread())
        assertSame(schedulers.io, Schedulers.io())
        assertSame(schedulers.computation, Schedulers.computation())
    }
}
