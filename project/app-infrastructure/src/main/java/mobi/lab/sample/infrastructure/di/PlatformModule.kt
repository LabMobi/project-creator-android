package mobi.lab.sample.infrastructure.di

import android.content.Context
import dagger.Module
import dagger.Provides
import mobi.lab.sample.infrastructure.common.http.ErrorTransformer
import mobi.lab.sample.infrastructure.common.http.MyErrorTransformer
import mobi.lab.sample.infrastructure.common.json.Json
import mobi.lab.sample.infrastructure.common.json.MoshiFactory
import mobi.lab.sample.infrastructure.common.json.MoshiJson
import mobi.lab.sample.infrastructure.common.platform.NetworkMonitor
import mobi.lab.sample.infrastructure.common.remote.error.ApiErrorResponseMapper
import javax.inject.Singleton

@Module
object PlatformModule {

    @Provides
    @Singleton
    internal fun provideJson(): Json = MoshiJson(MoshiFactory.get())

    @Provides
    @Singleton
    internal fun provideNetworkMonitor(context: Context): NetworkMonitor = NetworkMonitor(context)

    @Provides
    @Singleton
    internal fun provideErrorTransformer(networkMonitor: NetworkMonitor, errorMapper: ApiErrorResponseMapper, json: Json): ErrorTransformer {
        return MyErrorTransformer(networkMonitor, errorMapper, json)
    }
}
