package mobi.lab.sample.domain.gateway

import io.reactivex.rxjava3.core.Single
import mobi.lab.sample.domain.entities.Session

interface AuthGateway {
    fun login(username: String, password: String): Single<Session>
}
