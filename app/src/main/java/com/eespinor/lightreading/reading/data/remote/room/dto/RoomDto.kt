package com.eespinor.lightreading.reading.data.remote.room.dto

import com.eespinor.lightreading.reading.domain.room.model.Room

data class RoomDto(
    val id: String,
    val name: String
)

fun RoomDto.toRoom(): Room {
    return Room(
        id = id,
        name = name
    )
}
