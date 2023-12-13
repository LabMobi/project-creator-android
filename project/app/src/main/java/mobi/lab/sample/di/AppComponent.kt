package mobi.lab.sample.di

import dagger.Component
import mobi.lab.sample.infrastructure.di.GatewayModule
import mobi.lab.sample.infrastructure.di.MapperModule
import mobi.lab.sample.infrastructure.di.PlatformModule
import mobi.lab.sample.infrastructure.di.ResourceModule
import mobi.lab.sample.infrastructure.di.StorageModule
import javax.inject.Singleton

/**
 * Default Hilt component.
 */
@Singleton
@Component(
    modules = [
        ResourceModule::class,
        GatewayModule::class,
        MapperModule::class,
        StorageModule::class,
        AppModule::class,
        SchedulerModule::class,
        PlatformModule::class,
        /**
         * BuildVariantModule that can be overridden per build variant. No default implementation exists.
         * Instead, different build variants (flavours, build types) can provide a different implementation.
         */
        BuildVariantModule::class
    ]
)
interface AppComponent
