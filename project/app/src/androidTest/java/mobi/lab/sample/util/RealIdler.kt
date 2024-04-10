package mobi.lab.sample.util

import androidx.test.espresso.idling.CountingIdlingResource
import mobi.lab.sample.app.common.test.Idler

object RealIdler : Idler {
    val idlingResource: CountingIdlingResource = CountingIdlingResource("RealIdler", true)

    override fun increment() {
        idlingResource.increment()
    }

    override fun decrement() {
        idlingResource.decrement()
    }
}
