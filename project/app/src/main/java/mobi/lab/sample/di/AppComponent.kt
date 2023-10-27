package mobi.lab.sample.di

import dagger.Component
import mobi.lab.sample.domain.di.UseCaseModule
import mobi.lab.sample.infrastructure.di.GatewayModule
import mobi.lab.sample.infrastructure.di.MapperModule
import mobi.lab.sample.infrastructure.di.PlatformModule
import mobi.lab.sample.infrastructure.di.ResourceModule
import mobi.lab.sample.infrastructure.di.StorageModule
import javax.inject.Singleton

/**
 * Default implementation of [BaseAppComponent].
 *
 * @see [BaseAppComponent]
 */
@Singleton
@Component(
    modules = [
        ResourceModule::class,
        UseCaseModule::class,
        GatewayModule::class,
        MapperModule::class,
        StorageModule::class,
        AppModule::class,
        SchedulerModule::class,
        PlatformModule::class
    ]
)
interface AppComponent : BaseAppComponent
/**
 * DO NOT ADD METHODS HERE. Add methods to [BaseAppComponent].
 */
