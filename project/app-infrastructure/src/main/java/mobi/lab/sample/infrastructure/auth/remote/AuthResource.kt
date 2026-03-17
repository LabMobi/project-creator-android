package mobi.lab.sample.infrastructure.auth.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthResource {

    @Headers("x-api-key: reqres_1dcb793c2f5f4f209661c3c40b30dc46")
    @POST("login")
    fun login(@Body request: ApiLoginRequest): Single<ApiSession>
}
