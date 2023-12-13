package util

class PointProcessor {

    fun processPoint(x: Double, y: Double, r: Double): Boolean {
        return when {
            x >= 0 && y >= 0 && y <= r && x <= r / 2 -> true
            x <= 0 && y >= 0 -> false
            x <= 0 && y <= 0 && y >= -r && x >= -r / 2 && -r * x * 2 - r * r <= y * r -> true
            x >= 0 && y <= 0 && (x * x + y * y) <= (r * r) -> true
            else -> false
        }
    }
}
