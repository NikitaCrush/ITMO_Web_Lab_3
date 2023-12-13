package beans

import jakarta.enterprise.context.SessionScoped
import jakarta.inject.Named
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import java.io.Serializable

@Named
@SessionScoped
open class ClearResultsBean : Serializable {
    @PersistenceContext(unitName = "pointCheckerPU")
    private lateinit var entityManager: EntityManager

    @Transactional
    open fun clearResults() {
        entityManager.createQuery("DELETE FROM ResultData").executeUpdate()
    }
}
