package mobi.lab.sample.util

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import mobi.lab.sample.app.common.test.Idler
import mobi.lab.sample.common.rx.SchedulerProvider

class TestSchedulerProvider(private val idler: Idler) : SchedulerProvider() {
    override val main = AndroidSchedulers.mainThread()
    override val computation = Schedulers.computation()
    override val io = Schedulers.io()

    override fun <T : Any> Observable<T>.transformObservableInternal(): Observable<T> {
        val key = key()
        return this
            .doOnSubscribe { busy(key) }
            .doFinally { done(key) }
    }

    override fun <T : Any> Single<T>.transformSingleInternal(): Single<T> {
        val key = key()
        return this
            .doOnSubscribe { busy(key) }
            .doFinally { done(key) }
    }

    override fun Completable.transformCompletableInternal(): Completable {
        val key = key()
        return this
            .doOnSubscribe { busy(key) }
            .doFinally { done(key) }
    }

    override fun <T : Any> Maybe<T>.transformMaybeInternal(): Maybe<T> {
        val key = key()
        return this
            .doOnSubscribe { busy(key) }
            .doFinally { done(key) }
    }

    private fun key(): Any {
        return System.currentTimeMillis()
    }

    private fun busy(key: Any) {
        idler.increment()
//        BusyBee.singleton().busyWith(key)
    }

    private fun done(key: Any) {
        idler.decrement()
//        BusyBee.singleton().completed(key)
    }
}
