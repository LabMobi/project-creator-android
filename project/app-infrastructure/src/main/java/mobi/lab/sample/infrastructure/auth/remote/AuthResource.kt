package mobi.lab.sample.infrastructure.auth.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthResource {

    @POST("login")
    fun login(@Body request: ApiLoginRequest): Single<ApiSession>
}
