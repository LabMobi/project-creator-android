@file:Suppress("unused")

package mobi.lab.sample.infrastructure.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobi.lab.sample.domain.gateway.AuthGateway
import mobi.lab.sample.infrastructure.auth.AuthProvider

@Module(includes = [GatewayModule.Definitions::class])
@InstallIn(SingletonComponent::class)
object GatewayModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Definitions {
        @Binds fun bindAuthGateway(impl: AuthProvider): AuthGateway
    }
}
