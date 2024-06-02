package com.eespinor.lightreading.setting.domain.usecase

import com.eespinor.lightreading.setting.domain.model.Setting
import com.eespinor.lightreading.setting.domain.repository.SettingRepository
import javax.inject.Inject

class AddSetting @Inject constructor(
    private val settingRepository: SettingRepository,
) {
    suspend operator fun invoke(setting: Setting) = settingRepository.upsertSetting(setting)
}