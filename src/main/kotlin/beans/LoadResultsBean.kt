package beans

import jakarta.enterprise.context.SessionScoped
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.servlet.http.HttpSession
import java.io.Serializable

@Named
@SessionScoped
class LoadResultsBean : Serializable {
    @PersistenceContext(unitName = "pointCheckerPU")
    private lateinit var entityManager: EntityManager
    @Inject
    private lateinit var httpSession: HttpSession

    val results: List<ResultData>
        get() = entityManager.createQuery("SELECT r FROM ResultData r WHERE r.sessionId = :sessionId", ResultData::class.java)
            .setParameter("sessionId", httpSession.id)
            .resultList
}