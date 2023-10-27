package mobi.lab.sample.di

import mobi.lab.sample.App
import mobi.lab.sample.demo.login.LoginFragment
import mobi.lab.sample.demo.main.MainFragment
import mobi.lab.sample.demo.prototype.PrototypeActivity
import mobi.lab.sample.splash.SplashActivity

/**
 * Shared interface that is implemented by
 * 1. [AppComponent] that is the main component for our Dagger setup.
 * 2. [Injector] class that is a wrapper for convenient access to the [BaseAppComponent] instance for injection.
 * 3. Custom [BaseAppComponent] instance for instrumentation tests.
 *
 * The shared interface is convenient to keep the Injector and different Dagger components in sync when new injection entry points are added.
 * A shared interface will create a compile time error when some parts don't implement the interface properly. Without it, the errors would
 * appear only when the component is used (instrumentation tests are run for example).
 */
interface BaseAppComponent {
    fun inject(target: App)
    fun inject(target: SplashActivity)
    fun inject(target: PrototypeActivity)
    fun inject(target: MainFragment)
    fun inject(target: LoginFragment)
}
