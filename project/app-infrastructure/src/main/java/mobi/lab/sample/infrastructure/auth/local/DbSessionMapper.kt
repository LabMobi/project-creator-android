package mobi.lab.sample.infrastructure.auth.local

import mobi.lab.sample.domain.entities.Session
import javax.inject.Inject

class DbSessionMapper @Inject constructor() {

    fun toEntity(item: DbSession): Session {
        return Session(item.token)
    }

    fun toDb(item: Session): DbSession {
        return DbSession(item.token)
    }
}
