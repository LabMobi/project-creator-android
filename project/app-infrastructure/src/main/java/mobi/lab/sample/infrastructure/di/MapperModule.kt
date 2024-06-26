package mobi.lab.sample.infrastructure.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobi.lab.sample.infrastructure.common.remote.error.ApiErrorResponseMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    // Uses HttpException not available outside the package
    @Provides
    @Singleton
    fun provideErrorMapper(): ApiErrorResponseMapper = ApiErrorResponseMapper()

    /**
     * Use constructor injection for other mappers to avoid unnecessary boilerplate
     */
}
