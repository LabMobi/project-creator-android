package mobi.lab.sample.infrastructure.common.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit

class RetrofitFactory(private val errorTransformer: ErrorTransformer) {

    fun newInstance(
        baseUrl: String,
        httpClient: OkHttpClient,
        converterFactoryProvider: RetrofitConverterFactoryProvider
    ): Retrofit {
        return Retrofit.Builder()
            .validateEagerly(true)
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactoryProvider.get())
            .addCallAdapterFactory(RxErrorCallAdapterFactory.create(errorTransformer))
            .client(httpClient)
            .build()
    }
}
