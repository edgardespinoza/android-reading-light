package com.eespinor.lightreading.reading.domain.reading.usecase

import com.eespinor.lightreading.common.ValidationResult
import com.eespinor.lightreading.reading.domain.reading.model.ReadingType
import java.time.LocalDate
import javax.inject.Inject

class ValidateMonth @Inject constructor() {

    operator fun invoke(month: Int): ValidationResult<ReadingType> {

        if (month < LocalDate.now().monthValue - 1 || month > LocalDate.now().monthValue) {
            return ValidationResult(
                successful = false,
                type = ReadingType.MonthInvalid
            )
        }

        return ValidationResult(successful = true)
    }
}