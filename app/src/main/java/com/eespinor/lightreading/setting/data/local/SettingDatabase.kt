package com.eespinor.lightreading.reading.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SettingEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LightReadingDatabase: RoomDatabase(){
    abstract val dao:SettingDao
}