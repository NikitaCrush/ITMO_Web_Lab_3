package beans

import jakarta.enterprise.context.SessionScoped
import jakarta.faces.application.FacesMessage
import jakarta.faces.validator.ValidatorException
import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.servlet.http.HttpSession
import jakarta.transaction.Transactional
import util.PointProcessor
import java.io.Serializable

@Named
@SessionScoped
open class AreaCheckBean : Serializable {
    @PersistenceContext(unitName = "pointCheckerPU")
    private lateinit var entityManager: EntityManager

    var x: Double? = null
    var y: Double? = null
    var r: Double? = null
    var resultData: ResultData? = null

    private val pointProcessor = PointProcessor()

    @Inject
    private lateinit var httpSession: HttpSession
    @Transactional
    open fun checkArea() {
        val startTime = System.nanoTime()
        val result = pointProcessor.processPoint(x ?: 0.0, y ?: 0.0, r ?: 0.0)
        val endTime = System.nanoTime()
        val sessionId = httpSession.id
        resultData = ResultData(
            x = x ?: 0.0,
            y = y ?: 0.0,
            r = r ?: 0.0,
            result = result,
            executionTime = (endTime - startTime) / 1000,
            sessionId = sessionId

        )
        entityManager.persist(resultData)
    }

}
