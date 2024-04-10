package mobi.lab.sample.app.common.test

class NoOpIdler : Idler {
    override fun increment() {
        // No-op
    }

    override fun decrement() {
        // No-op
    }
}
