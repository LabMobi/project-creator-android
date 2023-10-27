package mobi.lab.sample.domain.usecases.auth

import dagger.Reusable
import io.reactivex.rxjava3.core.Completable
import mobi.lab.sample.domain.entities.DomainException
import mobi.lab.sample.domain.entities.ErrorCode
import mobi.lab.sample.domain.storage.SessionStorage
import javax.inject.Inject

@Reusable
class HasValidSessionUseCase @Inject constructor(private val sessionStorage: SessionStorage) {

    fun execute(): Completable {
        return Completable.fromSupplier {
            val session = sessionStorage.load()
            if (session == null || !session.isValid()) {
                throw DomainException(ErrorCode.LOCAL_UNAUTHORIZED)
            }
        }
    }
}
