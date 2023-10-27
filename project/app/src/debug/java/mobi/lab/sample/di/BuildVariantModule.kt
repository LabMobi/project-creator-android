package mobi.lab.sample.di

import dagger.Module
import dagger.Provides
import mobi.lab.sample.common.debug.DebugActions
import mobi.lab.sample.debug.DevDebugActions
import javax.inject.Singleton

@Module
object BuildVariantModule {

    @Provides
    @Singleton
    fun provideDebugActions(): DebugActions {
        return DevDebugActions()
    }
}
