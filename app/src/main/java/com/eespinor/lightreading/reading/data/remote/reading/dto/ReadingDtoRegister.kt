package com.eespinor.lightreading.reading.data.remote.reading.dto

import com.eespinor.lightreading.reading.domain.reading.model.Reading

data class ReadingDtoRegister (
    val roomId: String,
    val measure: Double,
    val year: Int,
    val month: Int,
)

fun Reading.toReadingDtoRegister() : ReadingDtoRegister {
    return ReadingDtoRegister (
        roomId = room.id,
        measure = measure,
        year = year,
        month = month
    )
}