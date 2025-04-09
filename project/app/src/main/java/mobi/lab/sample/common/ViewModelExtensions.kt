package mobi.lab.sample.common

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlin.reflect.KClass

/**
 * These extensions functions help with creating ViewModels with Assisted Injection factories.
 * There is a new CreationExtras feature that allows to pass in a callback when the ViewModel needs to be created.
 * HiltViewModels use this feature to provide a hook to invoke the custom AssistedInjection factory interface.
 *
 * These extension functions are just convenience functions around the extrasProducer field and make the API less verbose.
 *
 * NB! Similar extension functions are available in the https://github.com/LabMobi/mvvm-android. In hindsight, it wasn't the best
 * idea to include those extension functions into the MVVM library as they are specific to our project template's Dagger usage and
 * not connected to MVVM pattern as such.
 */

/**
 * Convenience function to init ViewModels lazily using Assisted Injection.
 * Wraps the assisted factory invocation with creation extras.
 *
 * The extra assistedViewModelFactoryClass argument is needed so we can invoke the assistedViewModelFactory with a properly typed argument.
 */
@Suppress("UNUSED_PARAMETER")
inline fun <reified VM : ViewModel, VMF : Any> ComponentActivity.assistedViewModels(
    assistedViewModelFactoryClass: KClass<VMF>,
    crossinline assistedViewModelFactory: (VMF) -> VM
): Lazy<VM> {
    return viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<VMF> { assistedViewModelFactory.invoke(it) }
        }
    )
}

/**
 * Convenience function to init ViewModels lazily using Assisted Injection.
 * Wraps the assisted factory invocation with creation extras.
 *
 * The extra assistedViewModelFactoryClass argument is needed so we can invoke the assistedViewModelFactory with a properly typed argument.
 */
@Suppress("UNUSED_PARAMETER", "unused")
inline fun <reified VM : ViewModel, VMF : Any> Fragment.assistedViewModels(
    assistedViewModelFactoryClass: KClass<VMF>,
    crossinline assistedViewModelFactory: (VMF) -> VM
): Lazy<VM> {
    return viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<VMF> { assistedViewModelFactory.invoke(it) }
        }
    )
}
