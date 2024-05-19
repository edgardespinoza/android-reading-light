package com.eespinor.lightreading.reading.data.remote.room

import com.eespinor.lightreading.reading.data.remote.room.dto.RoomDto
import retrofit2.Response
import retrofit2.http.GET

interface RoomApi {

    @GET("api/room")
    suspend fun getRooms(): List<RoomDto>
}