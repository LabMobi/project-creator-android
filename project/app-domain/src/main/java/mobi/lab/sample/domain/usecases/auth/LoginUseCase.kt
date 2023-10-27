package mobi.lab.sample.domain.usecases.auth

import dagger.Reusable
import io.reactivex.rxjava3.core.Single
import mobi.lab.sample.app.common.isStringEmpty
import mobi.lab.sample.domain.entities.DomainException
import mobi.lab.sample.domain.entities.ErrorCode
import mobi.lab.sample.domain.entities.Session
import mobi.lab.sample.domain.gateway.AuthGateway
import javax.inject.Inject

@Reusable
class LoginUseCase @Inject constructor(
    private val gw: AuthGateway,
    private val saveSessionUseCase: SaveSessionUseCase
) {
    fun execute(email: String, password: String): Single<Session> {
        if (isStringEmpty(email) || isStringEmpty(password)) {
            return Single.error(DomainException(ErrorCode.LOCAL_INVALID_CREDENTIALS))
        }
        // Just a local trick to allow error handling tests
        val magicWord = "test"
        val requestEmail: String
        val requestPassword: String
        if (email.contains(magicWord) || password.contains(magicWord)) {
            requestEmail = ""
            requestPassword = ""
        } else {
            requestEmail = "eve.holt@reqres.in"
            requestPassword = "cityslicka"
        }
        return gw.login(requestEmail, requestPassword).doOnSuccess { saveSessionUseCase.execute(it) }
    }
}
