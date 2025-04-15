@file:Suppress("unused")

package mobi.lab.sample.app.common

fun isStringEmpty(string: CharSequence?): Boolean {
    return string.isNullOrEmpty()
}

fun stringEquals(a: CharSequence?, b: CharSequence?): Boolean {
    // Taken from TextUtils.equals
    if (a === b) return true
    val length = a?.length ?: 0
    if (a != null && b != null && length == b.length) {
        return if (a is String && b is String) {
            a == b
        } else {
            charSequenceEqualsCharByChar(length, a, b)
        }
    }
    return false
}

private fun charSequenceEqualsCharByChar(compareLength: Int, a: CharSequence, b: CharSequence): Boolean {
    for (i in 0 until compareLength) {
        if (a[i] != b[i]) return false
    }
    return true
}

fun Any.className(): String {
    return this::class.java.name
}
