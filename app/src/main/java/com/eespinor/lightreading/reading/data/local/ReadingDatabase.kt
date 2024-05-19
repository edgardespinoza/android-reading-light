package com.eespinor.lightreading.reading.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.squareup.moshi.Json

@Database(
    entities = [RoomEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ReadingDatabase: RoomDatabase(){
    abstract val dao:ReadingDao
}