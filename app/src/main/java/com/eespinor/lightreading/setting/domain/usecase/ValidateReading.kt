package com.eespinor.lightreading.setting.domain.usecase

import com.eespinor.lightreading.common.ValidationResult
import com.eespinor.lightreading.setting.domain.model.SettingType
import javax.inject.Inject

class ValidateReading @Inject constructor() {

    operator fun invoke(reading: String): ValidationResult<SettingType> {
        if (reading.isEmpty()) {
            return ValidationResult(
                successful = false,
                SettingType.ReadingEmpty
            )
        }

        if (reading.toDoubleOrNull() == null) {
            return ValidationResult(
                successful = false,
                SettingType.ReadingNotNumber
            )
        }

        if ((reading.toDoubleOrNull() ?: 0.0) <= 0) {
            return ValidationResult(
                successful = false,
                SettingType.ReadingEmpty
            )
        }

        return ValidationResult(successful = true)
    }
}