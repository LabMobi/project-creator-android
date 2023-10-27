package mobi.lab.sample.infrastructure.di

import dagger.Binds
import dagger.Module
import mobi.lab.sample.domain.gateway.AuthGateway
import mobi.lab.sample.infrastructure.auth.AuthProvider

@Module(includes = [GatewayModule.Definitions::class])
object GatewayModule {

    @Module
    internal interface Definitions {
        @Binds fun bindAuthGateway(impl: AuthProvider): AuthGateway
    }
}
