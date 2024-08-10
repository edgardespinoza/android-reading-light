package com.eespinor.lightreading.setting.data.repository

import com.eespinor.lightreading.reading.data.local.SettingDao
import com.eespinor.lightreading.reading.data.local.toSetting
import com.eespinor.lightreading.setting.data.local.toSettingEntity
import com.eespinor.lightreading.setting.data.remote.SettingApi
import com.eespinor.lightreading.setting.data.remote.dto.toSetting
import com.eespinor.lightreading.setting.data.remote.dto.toSettingSaveDto
import com.eespinor.lightreading.setting.domain.model.Setting
import com.eespinor.lightreading.setting.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryLocal @Inject constructor(
    private val dao: SettingDao,
    private val api: SettingApi,
) : SettingRepository {

    override suspend fun getSetting(): Setting? {
        return dao.getSetting()?.toSetting()
    }

    override suspend fun upsertSetting(setting: Setting) {

        dao.upsertSetting(setting.toSettingEntity())
        try {
            api.saveSetting(setting.toSettingSaveDto())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override suspend fun getSettingRemote(): Setting? {
        val settingDao = api.getSetting()

        return settingDao?.toSetting()
    }

}