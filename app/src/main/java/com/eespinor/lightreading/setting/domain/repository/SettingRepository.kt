package com.eespinor.lightreading.setting.domain.repository

import com.eespinor.lightreading.setting.domain.model.Setting
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun getSetting(): Setting?

    suspend fun upsertSetting(setting: Setting)

    suspend fun getSettingRemote(): Setting?

}