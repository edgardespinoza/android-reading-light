package com.eespinor.lightreading.setting.presentation

data class SettingState(
    val id: Int? = null,
    val priceKwh: String = "",
    val total: String = "",
    val reading: String = "",
    val priceKwhError: Int? = null,
    val totalError: Int? = null,
    val readingError: Int? = null,
)