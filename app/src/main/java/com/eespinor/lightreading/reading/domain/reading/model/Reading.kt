package com.eespinor.lightreading.reading.domain.reading.model

import androidx.compose.runtime.Immutable
import com.eespinor.lightreading.reading.domain.room.model.Room

@Immutable
data class Reading (
    val id: String="",
    val measure: Double,
    val year: Int,
    val month: Int,
    val room: Room
)