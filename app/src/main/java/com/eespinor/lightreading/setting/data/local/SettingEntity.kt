package com.eespinor.lightreading.reading.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eespinor.lightreading.setting.domain.model.Setting

@Entity(tableName = "setting")
data class SettingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val priceKwh: Double,
    val total: Double,
    val reading: Double,
)

fun SettingEntity.toSetting(): Setting {
    return Setting(
        id = id,
        priceKwh = priceKwh,
        total = total,
        reading = reading
    )
}