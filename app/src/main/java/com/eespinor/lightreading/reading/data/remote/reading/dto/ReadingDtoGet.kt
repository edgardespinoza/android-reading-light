package com.eespinor.lightreading.reading.data.remote.reading.dto

import com.eespinor.lightreading.reading.domain.reading.model.Reading
import com.eespinor.lightreading.reading.domain.room.model.Room

data class RoomDtoGet(
    val id: String,
    val name: String,
)

data class ReadingDtoGet(
    val id: String,
    val measure: Double,
    val year: Int,
    val month: Int,
    val room: RoomDtoGet,
    val measurePrevious: Double,
)

fun ReadingDtoGet.toReading(): Reading {
    return Reading(
        id = id,
        measure = measure,
        year = year,
        month = month,
        room = Room(
            id = room.id,
            name = room.name
        ),
        measurePrevious = measurePrevious
    )
}
