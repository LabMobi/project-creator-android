package mobi.lab.sample.app.common.test

class NoOpIdler : Idler {

    override fun token(): IdlerToken {
        return IdlerToken(Any())
    }

    override fun busy(): IdlerToken {
        return IdlerToken(Any())
    }

    override fun busy(key: Any): IdlerToken {
        return IdlerToken(key)
    }

    override fun busy(token: IdlerToken) {
        // Do nothing
    }

    override fun done(key: Any) {
        // Do nothing
    }

    override fun done(token: IdlerToken) {
        // Do nothing
    }
}
