package mobi.lab.sample.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import mobi.lab.sample.Env
import mobi.lab.sample.common.util.isDebugBuild
import mobi.lab.sample.infrastructure.common.platform.AppEnvironment
import javax.inject.Singleton

/**
 * Includes
 * - ViewModelModule to provide all the different ViewModels we want to inject.
 * - BuildVariantModule that can be overridden per build variant. No default implementation exists.
 *   Instead, different build variants (flavours, build types) can provide a different implementation.
 */
@Module(includes = [ViewModelModule::class, BuildVariantModule::class])
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideAppEnvironment(): AppEnvironment = AppEnvironment(Env.URL_BASE, isDebugBuild())
}
