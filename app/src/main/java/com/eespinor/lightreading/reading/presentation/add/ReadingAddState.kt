package com.eespinor.lightreading.reading.presentation.add

import com.eespinor.lightreading.reading.domain.reading.model.Reading
import com.eespinor.lightreading.reading.domain.room.model.Room
import java.time.LocalDate

data class ReadingAddState(
    val isLoading: Boolean = false,
    val rooms: List<Room> = emptyList(),

    val measure: Double = 0.0,
    val measureError: Boolean = false,

    val roomId: String = "",
    val roomError: Boolean = false,
    val year: Int = LocalDate.now().year,
    val yearError: Boolean = false,
    val month: Int = LocalDate.now().monthValue,
    val monthError: Boolean = false,

    val isSuccessfullyRegistered:Boolean = false,

    val isErrorRegister: Boolean = false,
    val isErrorGetRooms: Boolean = false,
    val isErrorGetReading: Boolean = false,
    )