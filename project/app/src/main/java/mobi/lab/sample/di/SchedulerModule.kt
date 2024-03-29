package mobi.lab.sample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobi.lab.sample.common.rx.AndroidSchedulerProvider
import mobi.lab.sample.common.rx.SchedulerProvider
import javax.inject.Singleton

/**
 * SchedulerProvider is in a separate module to make it easier to provide alternative dependencies as needed.
 * For example, instrumentation (ui) tests are a good example where a different implementation for this is necessary.
 */
@Module
@InstallIn(SingletonComponent::class)
object SchedulerModule {
    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = AndroidSchedulerProvider()
}
