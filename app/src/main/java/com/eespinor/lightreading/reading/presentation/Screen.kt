package com.eespinor.lightreading.reading.presentation

import com.eespinor.lightreading.reading.domain.room.model.Room
import kotlinx.serialization.Serializable


sealed class Screen(val route: String) {
    object ReadingListScreen : Screen("reading_list_screen")
    object ReadingAddScreen : Screen("reading_add_screen")
}

@Serializable
data class ReadingEditScreen(
    val id: String,
    val measure: String,
    val year: Int,
    val month: Int,
    val roomId: String,
)



