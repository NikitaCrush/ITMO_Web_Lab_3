package beans

import jakarta.enterprise.context.SessionScoped
import jakarta.inject.Named
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import java.io.Serializable

@Named
@SessionScoped
class LoadResultsBean : Serializable {
    @PersistenceContext(unitName = "pointCheckerPU")
    private lateinit var entityManager: EntityManager

    val results: List<ResultData>
        get() = entityManager.createQuery("SELECT r FROM ResultData r", ResultData::class.java).resultList
}