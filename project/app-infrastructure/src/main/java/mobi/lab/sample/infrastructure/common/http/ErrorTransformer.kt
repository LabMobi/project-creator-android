package mobi.lab.sample.infrastructure.common.http

interface ErrorTransformer {
    fun transform(error: Throwable): Throwable
}
