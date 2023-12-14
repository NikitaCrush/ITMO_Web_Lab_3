package beans

import jakarta.enterprise.context.SessionScoped
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.servlet.http.HttpSession
import jakarta.transaction.Transactional
import java.io.Serializable

@Named
@SessionScoped
open class ClearResultsBean : Serializable {
    @PersistenceContext(unitName = "pointCheckerPU")
    private lateinit var entityManager: EntityManager

    @Inject
    private lateinit var httpSession: HttpSession

    @Transactional
    open fun clearResults() {
        val sessionId = httpSession.id
        entityManager.createQuery("DELETE FROM ResultData r WHERE r.sessionId = :sessionId")
            .setParameter("sessionId", sessionId)
            .executeUpdate()
    }
}
