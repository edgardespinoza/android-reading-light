package com.eespinor.lightreading.setting.data.remote

import com.eespinor.lightreading.setting.data.remote.dto.SettingGetDto
import com.eespinor.lightreading.setting.data.remote.dto.SettingSaveDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SettingApi {
    @GET("api/setting")
    suspend fun getSetting(): SettingGetDto?

    @POST("api/setting")
    suspend fun saveSetting(@Body setting: SettingSaveDto)

}