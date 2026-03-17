@file:Suppress("unused")

package mobi.lab.sample.domain.entities

class DomainException(val code: ErrorCode, cause: Throwable? = null, message: String? = "DomainException: errorCode=$code",) : Exception(message) {

    constructor(code: ErrorCode, cause: Throwable) : this(code = code, cause = cause, message = null)
    constructor(code: ErrorCode, message: String) : this(code = code, cause = null, message = message)

    init {
        if (cause != null) {
            this.initCause(cause)
        }
    }

    fun isFor(errorCode: ErrorCode): Boolean = errorCode == code

    companion object {
        fun unknown(): DomainException = DomainException(ErrorCode.UNKNOWN)

        fun unauthorized(cause: Throwable? = null): DomainException = DomainException(ErrorCode.LOCAL_UNAUTHORIZED, cause)
    }
}
