package mobi.lab.sample.infrastructure.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mobi.lab.sample.infrastructure.common.http.ErrorTransformer
import mobi.lab.sample.infrastructure.common.http.MyErrorTransformer
import mobi.lab.sample.infrastructure.common.json.Json
import mobi.lab.sample.infrastructure.common.json.MoshiFactory
import mobi.lab.sample.infrastructure.common.json.MoshiJson
import mobi.lab.sample.infrastructure.common.platform.NetworkMonitor
import mobi.lab.sample.infrastructure.common.remote.error.ApiErrorResponseMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlatformModule {

    @Provides
    @Singleton
    internal fun provideJson(): Json = MoshiJson(MoshiFactory.get())

    @Provides
    @Singleton
    internal fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor = NetworkMonitor(context)

    @Provides
    @Singleton
    internal fun provideErrorTransformer(networkMonitor: NetworkMonitor, errorMapper: ApiErrorResponseMapper, json: Json): ErrorTransformer {
        return MyErrorTransformer(networkMonitor, errorMapper, json)
    }
}
