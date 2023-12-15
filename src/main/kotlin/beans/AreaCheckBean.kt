package beans

import jakarta.annotation.PostConstruct
import jakarta.enterprise.context.SessionScoped
import jakarta.faces.context.FacesContext
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

    private var points: List<ResultData> = mutableListOf()

    @PostConstruct
    fun init() {
        loadPointsFromDb()  // Load points when the bean is initialized
    }

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

    @Transactional
    open fun addFromJS() {
        val facesContext = FacesContext.getCurrentInstance()
        val requestMap = facesContext.externalContext.requestParameterMap

        x = requestMap["x"]?.toDoubleOrNull()
        y = requestMap["y"]?.toDoubleOrNull()
        r = requestMap["r"]?.toDoubleOrNull()

        x?.let { xVal ->
            y?.let { yVal ->
                r?.let { rVal ->
                    val result = pointProcessor.processPoint(xVal, yVal, rVal)
                    val endTime = System.nanoTime()
                    val sessionId = httpSession.id
                    resultData = ResultData(
                        x = xVal,
                        y = yVal,
                        r = rVal,
                        result = result,
                        executionTime = (endTime - System.nanoTime()) / 1000,
                        sessionId = sessionId
                    )
                    entityManager.persist(resultData)
                }
            }
        }
    }

    @Transactional
    open fun loadPointsFromDb() {
        val sessionId = httpSession.id
        points = entityManager.createQuery(
            "SELECT r FROM ResultData r WHERE r.sessionId = :sessionId",
            ResultData::class.java
        ).setParameter("sessionId", sessionId).resultList
    }

    fun getPoints(): List<ResultData> = points
}
