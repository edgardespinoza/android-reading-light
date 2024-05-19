package com.eespinor.lightreading.reading.domain.repository

import com.eespinor.lightreading.common.Resource
import com.eespinor.lightreading.reading.domain.room.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    suspend fun getRooms(): Flow<Resource<List<Room>>>

}