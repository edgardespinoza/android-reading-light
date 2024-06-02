package com.eespinor.lightreading.setting.domain.usecase

import com.eespinor.lightreading.common.ValidationResult
import com.eespinor.lightreading.setting.domain.model.SettingType
import javax.inject.Inject

class ValidateTotal @Inject constructor() {

    operator fun invoke(total: String): ValidationResult<SettingType> {
        if (total.isEmpty()) {
            return ValidationResult(
                successful = false,
                SettingType.TotalEmpty
            )
        }

        if (total.toDoubleOrNull() == null) {
            return ValidationResult(
                successful = false,
                SettingType.TotalNotNumber
            )
        }

        if ((total.toDoubleOrNull() ?: 0.0) <= 0) {
            return ValidationResult(
                successful = false,
                SettingType.TotalEmpty
            )
        }

        return ValidationResult(successful = true)
    }
}