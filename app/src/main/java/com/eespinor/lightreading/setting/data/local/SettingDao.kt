package com.eespinor.lightreading.reading.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDao{

    @Query("SELECT * FROM setting LIMIT 1")
    suspend fun getSetting(): SettingEntity?

    @Upsert
    suspend fun upsertSetting(setting: SettingEntity)

}