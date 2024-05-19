package com.eespinor.lightreading.reading.presentation.list

import com.eespinor.lightreading.reading.domain.reading.model.Reading
import com.eespinor.lightreading.reading.domain.room.model.Room
import java.time.LocalDate

data class ReadingListState (
    val isLoading: Boolean = false,
    val readings: List<Reading> = emptyList(),
    val year: Int = LocalDate.now().year,
    val month: Int = LocalDate.now().monthValue,
    val error: String = ""
)