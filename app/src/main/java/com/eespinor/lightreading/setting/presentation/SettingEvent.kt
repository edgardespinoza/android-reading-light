package com.eespinor.lightreading.setting.presentation

sealed class SettingEvent {
    data class OnPriceKwhChanged(val priceKwh: String) : SettingEvent()
    data class OnTotalChanged(val total: String) : SettingEvent()
    data class OnReadingChanged(val reading: String) : SettingEvent()
    data object OnSubmit : SettingEvent()

    data object OnCalculatePrice: SettingEvent()

}