package com.eespinor.lightreading.setting.domain.model

data class Setting(
    val id: Int? = null,
    val priceKwh: Double,
    val total: Double,
    val reading: Double,
)

