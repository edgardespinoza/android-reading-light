package com.eespinor.lightreading.reading.presentation.add

import com.eespinor.lightreading.reading.domain.room.model.Room
import java.time.LocalDate

data class ReadingAddState(
    val isLoading: Boolean = false,
    val rooms: List<Room> = emptyList(),

    val measure: String = "",
    val measureError: Int? = null,

    val roomId: String = "",
    val roomError: Int? = null,
    val year: Int = LocalDate.now().year,
    val yearError: Int? = null,
    val month: Int = LocalDate.now().monthValue,
    val monthError: Int? = null,

    val isErrorGetRooms: Boolean = false,
    val isErrorGetReading: Boolean = false,
)