package com.eespinor.lightreading.reading.domain.reading.usecase

import com.eespinor.lightreading.common.ValidationResult
import com.eespinor.lightreading.reading.domain.reading.model.ReadingType
import java.time.LocalDate
import javax.inject.Inject

class ValidateMeasure @Inject constructor() {

    operator fun invoke(measure: String): ValidationResult<ReadingType> {
        if (measure.isEmpty()) {
            return ValidationResult(
                successful = false,
                ReadingType.MeasureEmpty
            )
        }

        if (measure.toDoubleOrNull() == null) {
            return ValidationResult(
                successful = false,
                ReadingType.MeasureIsNotNumber
            )
        }

        if ((measure.toDoubleOrNull() ?: 0.0) <= 0) {
            return ValidationResult(
                successful = false,
                ReadingType.MeasureEmpty
            )
        }

        return ValidationResult(successful = true)
    }
}