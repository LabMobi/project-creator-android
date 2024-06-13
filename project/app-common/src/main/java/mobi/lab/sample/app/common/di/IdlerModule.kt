package mobi.lab.sample.app.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobi.lab.sample.app.common.test.Idler
import mobi.lab.sample.app.common.test.NoOpIdler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IdlerModule {

    @Provides
    @Singleton
    internal fun provideIdler(): Idler = NoOpIdler()
}
