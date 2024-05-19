package com.eespinor.lightreading.reading.domain.reading.usecase

import com.eespinor.lightreading.common.ValidationResult
import com.eespinor.lightreading.reading.domain.reading.model.ReadingType
import java.time.LocalDate
import javax.inject.Inject

class ValidateYear @Inject constructor() {

    operator fun invoke(year: Int): ValidationResult<ReadingType> {
        if (year < LocalDate.now().year - 1 || year > LocalDate.now().year) {
            return ValidationResult(
                successful = false,
                type = ReadingType.YearInvalid
            )
        }
        return ValidationResult(successful = true)
    }
}