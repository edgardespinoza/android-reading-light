package com.eespinor.lightreading.reading.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.RoomDatabase
import com.eespinor.lightreading.reading.domain.room.model.Room
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.Flow

@Dao
interface ReadingDao{

    @Query("SELECT * FROM room")
    fun getRooms(): Flow<List<RoomEntity>>
}