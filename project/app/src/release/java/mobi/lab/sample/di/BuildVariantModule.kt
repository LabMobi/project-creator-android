package mobi.lab.sample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobi.lab.sample.common.debug.DebugActions
import mobi.lab.sample.debug.ReleaseDebugActions
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BuildVariantModule {

    @Provides
    @Singleton
    fun provideDebugActions(): DebugActions {
        return ReleaseDebugActions()
    }
}
