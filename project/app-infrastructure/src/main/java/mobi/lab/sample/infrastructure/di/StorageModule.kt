package mobi.lab.sample.infrastructure.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobi.lab.sample.domain.storage.SessionStorage
import mobi.lab.sample.infrastructure.auth.local.SessionPreferenceStorage

/**
 * Storage implementation are provided by constructor injection from db package
 */
@Module(includes = [StorageModule.Definitions::class])
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Module
    @InstallIn(SingletonComponent::class)
    internal interface Definitions {
        @Binds fun bindSessionStorage(impl: SessionPreferenceStorage): SessionStorage
    }
}
