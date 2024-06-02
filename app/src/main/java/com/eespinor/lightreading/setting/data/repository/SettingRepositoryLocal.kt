package com.eespinor.lightreading.setting.data.repository

import com.eespinor.lightreading.reading.data.local.SettingDao
import com.eespinor.lightreading.reading.data.local.toSetting
import com.eespinor.lightreading.setting.data.local.toSettingEntity
import com.eespinor.lightreading.setting.domain.model.Setting
import com.eespinor.lightreading.setting.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryLocal @Inject constructor(
    private val dao: SettingDao,
) : SettingRepository {

    override suspend fun getSetting(): Setting? {
        return dao.getSetting()?.toSetting()
    }

    override suspend fun upsertSetting(setting: Setting) {
        dao.upsertSetting(setting.toSettingEntity())
    }

}