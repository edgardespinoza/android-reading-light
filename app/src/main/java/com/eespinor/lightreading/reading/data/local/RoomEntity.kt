package com.eespinor.lightreading.reading.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room")
data class RoomEntity(
    @PrimaryKey
    val id: String,
    val name: String
)