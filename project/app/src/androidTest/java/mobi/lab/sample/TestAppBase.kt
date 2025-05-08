package mobi.lab.sample

import android.app.Application
import androidx.test.espresso.IdlingRegistry
import mobi.lab.sample.util.RealIdler
import timber.log.Timber

open class TestAppBase : Application() {

    override fun onCreate() {
        super.onCreate()
        // Any other relevant setup we need to make
        Timber.plant(Timber.DebugTree())
        IdlingRegistry.getInstance().register(RealIdler.idlingResource)
    }
}
