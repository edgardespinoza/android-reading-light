package com.eespinor.lightreading.reading.domain.reading.model

import androidx.compose.runtime.Immutable
import com.eespinor.lightreading.reading.domain.room.model.Room
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Locale

@Immutable
data class Reading(
    val id: String = "",
    val measure: Double,
    val year: Int,
    val month: Int,
    val room: Room,
    val measurePrevious: Double? = 0.0,
    val differenceMeasure: Double? = 0.0,
    val amountPaid: Int? = 0,
)

fun calculateDifferenceMeasure(measure:Double, measurePrevious: Double?): Double {
    val result = measure - (measurePrevious ?: 0.0)

    return BigDecimal(result).setScale(2, RoundingMode.HALF_UP).toDouble()
}

fun calculateAmountPaid(priceKwh: Double?, differenceMeasure:Double): Int {
    priceKwh?.let {
        val amountPaid = differenceMeasure * it

        return  amountPaid.toInt()

    }
    return 0
}
