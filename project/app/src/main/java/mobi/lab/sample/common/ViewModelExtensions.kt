package mobi.lab.sample.common

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlin.reflect.KClass

/**
 * Activity based Assisted Injection
 *
 * Convenience function to init ViewModels lazily using Assisted Injection.
 * Wraps the assisted factory invocation with creation extras.
 *
 * The extra class argument is needed so we can invoke the pr
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
 * Activity based Assisted Injection
 *
 * Convenience function to init ViewModels lazily using Assisted Injection.
 * Wraps the assisted factory invocation with creation extras.
 */
@Suppress("UNUSED_PARAMETER")
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
