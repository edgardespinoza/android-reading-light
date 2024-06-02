package com.eespinor.lightreading.setting.domain.usecase

import com.eespinor.lightreading.common.ValidationResult
import com.eespinor.lightreading.setting.domain.model.SettingType
import javax.inject.Inject

class ValidatePriceKwh @Inject constructor() {

    operator fun invoke(priceKwh: String): ValidationResult<SettingType> {
        if (priceKwh.isEmpty()) {
            return ValidationResult(
                successful = false,
                SettingType.PriceKwhEmpty
            )
        }

        if (priceKwh.toDoubleOrNull() == null) {
            return ValidationResult(
                successful = false,
                SettingType.PriceKwhNotNumber
            )
        }

        if ((priceKwh.toDoubleOrNull() ?: 0.0) <= 0) {
            return ValidationResult(
                successful = false,
                SettingType.PriceKwhEmpty
            )
        }

        return ValidationResult(successful = true)
    }
}