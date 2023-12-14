package beans

import jakarta.enterprise.context.SessionScoped
import jakarta.faces.application.FacesMessage
import jakarta.faces.validator.ValidatorException
import jakarta.inject.Named
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
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


    @Transactional
    open fun checkArea() {
        val startTime = System.nanoTime()
        val result = pointProcessor.processPoint(x ?: 0.0, y ?: 0.0, r ?: 0.0)
        val endTime = System.nanoTime()
        resultData = ResultData(
            x = x ?: 0.0,
            y = y ?: 0.0,
            r = r ?: 0.0,
            result = result,
            executionTime = (endTime - startTime) / 1000
        )

//        fun validateY(y: Double?) {
//            if (!(y is Number)) {
//                val message = FacesMessage("Y must be numeric")
//                throw ValidatorException(message)
//            }
//
//            if ((y < -5) || (y > 3)) {
//                val message = FacesMessage("y needs to be > -5 and < 3")
//                throw ValidatorException(message)
//            }
//        }
//        fun validateR(r: Double?) {
//            if (!(r is Number)) {
//                val message = FacesMessage("R must be numeric")
//                throw ValidatorException(message)
//            }
//
//            if ((r < 2) || (r > 5)) {
//                val message = FacesMessage("r needs to be > 2 and < 5")
//                throw ValidatorException(message)
//            }
//        }

        entityManager.persist(resultData)
    }

}
