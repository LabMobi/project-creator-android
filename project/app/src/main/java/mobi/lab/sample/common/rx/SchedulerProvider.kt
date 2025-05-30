@file:Suppress("unused")

package mobi.lab.sample.common.rx

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableTransformer
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.MaybeTransformer
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleTransformer
import mobi.lab.sample.common.platform.LogoutMonitor
import mobi.lab.sample.domain.entities.DomainException
import mobi.lab.sample.domain.entities.ErrorCode

abstract class SchedulerProvider {
    abstract val main: Scheduler
    abstract val computation: Scheduler
    abstract val io: Scheduler

    protected open fun <T : Any> Observable<T>.transformObservableInternal(): Observable<T> {
        // No-op. Override to add custom behaviour. Useful for testing environments.
        return this
    }

    protected open fun <T : Any> Single<T>.transformSingleInternal(): Single<T> {
        // No-op. Override to add custom behaviour. Useful for testing environments.
        return this
    }

    protected open fun Completable.transformCompletableInternal(): Completable {
        // No-op. Override to add custom behaviour. Useful for testing environments.
        return this
    }

    protected open fun <T : Any> Maybe<T>.transformMaybeInternal(): Maybe<T> {
        // No-op. Override to add custom behaviour. Useful for testing environments.
        return this
    }

    fun <T : Any> observable(subscribeOn: Scheduler = io, observeOn: Scheduler = main): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .doOnError(::checkUnauthorizedError)
                .transformObservableInternal()
        }
    }

    fun <T : Any> single(subscribeOn: Scheduler = io, observeOn: Scheduler = main): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .doOnError(::checkUnauthorizedError)
                .transformSingleInternal()
        }
    }

    fun completable(subscribeOn: Scheduler = io, observeOn: Scheduler = main): CompletableTransformer {
        return CompletableTransformer {
            it.subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .doOnError(::checkUnauthorizedError)
                .transformCompletableInternal()
        }
    }

    fun completableNoAuthCheck(subscribeOn: Scheduler = io, observeOn: Scheduler = main): CompletableTransformer {
        return CompletableTransformer {
            it.subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .transformCompletableInternal()
        }
    }

    fun <T : Any> maybe(subscribeOn: Scheduler = io, observeOn: Scheduler = main): MaybeTransformer<T, T> {
        return MaybeTransformer {
            it.subscribeOn(subscribeOn)
                .observeOn(observeOn)
                .doOnError(::checkUnauthorizedError)
                .transformMaybeInternal()
        }
    }

    private fun checkUnauthorizedError(error: Throwable) {
        if (error is DomainException && error.isFor(ErrorCode.LOCAL_UNAUTHORIZED)) {
            LogoutMonitor.logout()
        }
    }
}
