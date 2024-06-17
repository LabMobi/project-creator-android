package mobi.lab.sample.infrastructure.common.json

import androidx.annotation.Keep
import java.lang.reflect.Type

@Keep
interface Json {
    fun <T : Any> toJson(item: T?, typeToken: Type): String
    fun <T : Any> fromJson(rawJson: String, typeToken: Type): T?
}

inline fun <reified T : Any> Json.toJson(item: T?): String {
    return toJson(item, T::class.java)
}

inline fun <reified T : Any> Json.fromJson(rawJson: String): T? {
    return fromJson(rawJson, T::class.java)
}
