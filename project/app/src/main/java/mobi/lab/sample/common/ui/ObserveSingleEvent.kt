package mobi.lab.sample.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import mobi.lab.mvvm.SingleEvent

/**
 * Observes a [LiveData] emitting [SingleEvent] values in Compose, consuming each event at most once
 * while respecting the current [LocalLifecycleOwner].
 */
@Composable
fun <T> ObserveSingleEvent(
    liveData: LiveData<SingleEvent<T>>,
    onEvent: (T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentOnEvent by rememberUpdatedState(onEvent)

    DisposableEffect(liveData, lifecycleOwner) {
        val observer = Observer<SingleEvent<T>> { event ->
            event.getContentIfNotHandled()?.let(currentOnEvent)
        }
        liveData.observe(lifecycleOwner, observer)
        onDispose {
            liveData.removeObserver(observer)
        }
    }
}
