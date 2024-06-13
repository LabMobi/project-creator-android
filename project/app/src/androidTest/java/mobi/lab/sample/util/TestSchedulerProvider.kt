package mobi.lab.sample.util

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import mobi.lab.sample.app.common.test.Idler
import mobi.lab.sample.common.rx.SchedulerProvider

class TestSchedulerProvider(private val idler: Idler) : SchedulerProvider() {

    override val main: Scheduler = AndroidSchedulers.mainThread()
    override val computation: Scheduler = Schedulers.computation()
    override val io: Scheduler = Schedulers.io()

    override fun <T : Any> Observable<T>.transformObservableInternal(): Observable<T> {
        val token = idler.token()
        return this
            .doOnSubscribe { idler.busy(token) }
            .doFinally { idler.done(token) }
    }

    override fun <T : Any> Single<T>.transformSingleInternal(): Single<T> {
        val token = idler.token()
        return this
            .doOnSubscribe { idler.busy(token) }
            .doFinally { idler.done(token) }
    }

    override fun Completable.transformCompletableInternal(): Completable {
        val token = idler.token()
        return this
            .doOnSubscribe { idler.busy(token) }
            .doFinally { idler.done(token) }
    }

    override fun <T : Any> Maybe<T>.transformMaybeInternal(): Maybe<T> {
        val token = idler.token()
        return this
            .doOnSubscribe { idler.busy(token) }
            .doFinally { idler.done(token) }
    }
}
