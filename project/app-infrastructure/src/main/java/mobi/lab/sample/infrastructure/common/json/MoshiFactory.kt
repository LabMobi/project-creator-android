package mobi.lab.sample.infrastructure.common.json

import androidx.annotation.Keep
import com.squareup.moshi.Moshi
import java.time.Instant
import java.time.OffsetDateTime

@Keep
object MoshiFactory {

    private val engine: Moshi by lazy {
        Moshi.Builder()
            .add(OffsetDateTime::class.java, MoshiOffsetDateTimeAdapter())
            .add(Instant::class.java, MoshiInstantAdapter())
            .add(ApplicationJsonAdapterFactory)
            .build()
    }

    fun get(): Moshi {
        return engine
    }
}
