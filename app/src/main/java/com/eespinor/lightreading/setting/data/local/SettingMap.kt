package com.eespinor.lightreading.setting.data.local

import com.eespinor.lightreading.reading.data.local.SettingEntity
import com.eespinor.lightreading.setting.domain.model.Setting

fun Setting.toSettingEntity(): SettingEntity {
    return SettingEntity(
        id = id,
        priceKwh = priceKwh,
        total = total,
        reading = reading
    )
}