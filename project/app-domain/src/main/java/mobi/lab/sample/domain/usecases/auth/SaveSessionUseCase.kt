package mobi.lab.sample.domain.usecases.auth

import dagger.Reusable
import mobi.lab.sample.domain.entities.Session
import mobi.lab.sample.domain.storage.SessionStorage
import javax.inject.Inject

@Reusable
class SaveSessionUseCase @Inject constructor(private val sessionStorage: SessionStorage) {

    fun execute(session: Session) {
        sessionStorage.save(session)
    }
}
