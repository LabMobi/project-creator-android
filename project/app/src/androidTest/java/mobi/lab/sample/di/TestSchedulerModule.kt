package mobi.lab.sample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import mobi.lab.sample.app.common.test.Idler
import mobi.lab.sample.common.rx.SchedulerProvider
import mobi.lab.sample.util.TestSchedulerProvider
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SchedulerModule::class]
)
object TestSchedulerModule {

    @Singleton
    @Provides
    fun provideSchedulerProvider(idler: Idler): SchedulerProvider {
        return TestSchedulerProvider(idler)
    }
}
