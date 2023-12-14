package beans

import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDateTime


@Entity
@Table(name = "results")
data class ResultData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column
    val x: Double = 0.0,

    @Column
    val y: Double = 0.0,

    @Column
    val r: Double = 0.0,

    @Column
    val result: Boolean = false,

    @Column(name = "timestamp")
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @Column(name = "execution_time")
    val executionTime: Long = 0,

    @Column(name = "session_id")
    val sessionId: String? = null

) : Serializable
