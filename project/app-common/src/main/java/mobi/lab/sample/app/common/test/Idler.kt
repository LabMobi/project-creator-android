package mobi.lab.sample.app.common.test

interface Idler {
    /**
     * Create a new [IdlerToken]. This does not mark the token as busy
     *
     * @return new [IdlerToken]
     */
    fun token(): IdlerToken

    /**
     * Create a new [IdlerToken] and mark it as busy.
     *
     * @return new [IdlerToken]
     */
    fun busy(): IdlerToken

    /**
     * Create a new [IdlerToken] from the given key and mark it as busy.
     *
     * @param key Any object to use as a value for the token.
     * @return new [IdlerToken] using the key
     */
    fun busy(key: Any): IdlerToken

    /**
     * Mark the [IdlerToken] as busy.
     *
     * @param token [IdlerToken]
     */
    fun busy(token: IdlerToken)

    /**
     * Mark work identified by key as done.
     *
     * @param key Any object that identifies the work
     */
    fun done(key: Any)

    /**
     * Mark work identified by token as done.
     *
     * @param token [IdlerToken]
     */
    fun done(token: IdlerToken)
}
