package com.eespinor.lightreading.setting.data.remote.dto

import com.eespinor.lightreading.setting.domain.model.Setting

data class SettingGetDto (val measure: Double, val priceKwh: Double, val totalPrice: Double)

fun SettingGetDto.toSetting(): Setting {
    return Setting(
        priceKwh = priceKwh,
        total = totalPrice,
        reading = measure
    )
}

data class SettingSaveDto (val measure: Double, val totalPrice: Double)

fun Setting.toSettingSaveDto(): SettingSaveDto {
    return SettingSaveDto(
        measure = reading ,
        totalPrice = total
    )
}