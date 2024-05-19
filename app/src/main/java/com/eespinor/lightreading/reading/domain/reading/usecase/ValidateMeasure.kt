package com.eespinor.lightreading.reading.domain.reading.usecase

import com.eespinor.lightreading.common.ValidationResult
import com.eespinor.lightreading.reading.domain.reading.model.ReadingType
import java.time.LocalDate
import javax.inject.Inject

class ValidateMeasure @Inject constructor() {

    operator fun invoke(measure: Double): ValidationResult<ReadingType> {

        if (measure <= 0) {
            return ValidationResult(
                successful = false,
                ReadingType.MeasureEmpty
            )
        }

        return ValidationResult(successful = true)
    }
}