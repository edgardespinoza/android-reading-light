package com.eespinor.lightreading.setting.domain.usecase

import com.eespinor.lightreading.common.ValidationResult
import com.eespinor.lightreading.setting.domain.model.SettingType
import javax.inject.Inject

class CalculatePrice @Inject constructor() {

    operator fun invoke(total: Double, reading: Double): Double {
        return total/reading
    }
}