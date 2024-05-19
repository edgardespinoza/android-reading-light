package com.eespinor.lightreading.reading.domain.reading.usecase

import com.eespinor.lightreading.common.ValidationResult
import com.eespinor.lightreading.reading.domain.reading.model.ReadingType
import java.time.LocalDate
import javax.inject.Inject

class ValidateRoom @Inject constructor() {

    operator fun invoke(roomId: String): ValidationResult<ReadingType> {
        if (roomId.isEmpty()) {
            return ValidationResult(
                successful = false,
                type = ReadingType.RoomEmpty
            )
        }
        return ValidationResult(successful = true)
    }
}