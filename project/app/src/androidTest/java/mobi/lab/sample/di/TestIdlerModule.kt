package mobi.lab.sample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import mobi.lab.sample.app.common.di.IdlerModule
import mobi.lab.sample.app.common.test.Idler
import mobi.lab.sample.util.RealIdler
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [IdlerModule::class]
)
object TestIdlerModule {

    @Singleton
    @Provides
    fun provideIdler(): Idler {
        return RealIdler
    }
}
