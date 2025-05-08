package mobi.lab.sample.util

import androidx.test.espresso.idling.CountingIdlingResource
import mobi.lab.sample.app.common.test.Idler
import mobi.lab.sample.app.common.test.IdlerToken
import timber.log.Timber
import java.util.Collections
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

object RealIdler : Idler {
    // NB! Do not forget to register this with IdlingRegistry.
    val idlingResource: CountingIdlingResource = CountingIdlingResource("RealIdler", true)

    internal val set = Collections.newSetFromMap(ConcurrentHashMap<Any, Boolean>())

    override fun token(): IdlerToken {
        return IdlerToken(newKey())
    }

    override fun busy(): IdlerToken {
        return busyInternal(newKey())
    }

    override fun busy(key: Any): IdlerToken {
        return busyInternal(key)
    }

    override fun busy(token: IdlerToken) {
        busyInternal(token.key)
    }

    override fun done(key: Any) {
        doneInternal(key)
    }

    override fun done(token: IdlerToken) {
        doneInternal(token.key)
    }

    private fun busyInternal(key: Any): IdlerToken {
        debugLog("busyInternal key=$key set=$set")
        idlingResource.dumpStateToLogs()

        val added = set.add(key)
        if (!added) {
            throw RuntimeException("Idler is already busy with key $key. Invalid state when marking $key as busy")
        }
        idlingResource.increment()
        return IdlerToken(key)
    }

    private fun doneInternal(key: Any) {
        debugLog("doneInternal key=$key set=$set")
        idlingResource.dumpStateToLogs()

        val removed = set.remove(key)
        if (!removed) {
            throw RuntimeException("Idler is not busy with key $key. Invalid state when marking $key as done.")
        }
        idlingResource.decrement()
    }

    private fun debugLog(message: String) {
        Timber.v(message)
    }

    private fun newKey(): Any {
        return UUID.randomUUID()
    }
}
