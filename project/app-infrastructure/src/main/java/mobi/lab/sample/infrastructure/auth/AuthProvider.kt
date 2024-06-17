package mobi.lab.sample.infrastructure.auth

import io.reactivex.rxjava3.core.Single
import mobi.lab.sample.domain.entities.Session
import mobi.lab.sample.domain.gateway.AuthGateway
import mobi.lab.sample.infrastructure.auth.remote.ApiLoginRequest
import mobi.lab.sample.infrastructure.auth.remote.ApiSessionMapper
import mobi.lab.sample.infrastructure.auth.remote.AuthResource
import javax.inject.Inject

class AuthProvider @Inject constructor(
    private val res: AuthResource,
    private val sessionMapper: ApiSessionMapper
) : AuthGateway {

    override fun login(username: String, password: String): Single<Session> {
        return res.login(ApiLoginRequest(email = username, password = password))
            .map(sessionMapper::toEntity)
    }
}
