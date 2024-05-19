package com.eespinor.lightreading.reading.presentation

import kotlinx.serialization.Serializable


sealed class Screen(val route: String) {
    object ReadingListScreen : Screen("reading_list_screen")
    object ReadingAddScreen : Screen("reading_add_screen")
}

sealed class ScreenNew{
    @Serializable
    object ReadingListScreenRoute
    @Serializable
    data class ReadingAddScreenRoute(
        val id: String,
        val measure: Double,
        val year: Int,
        val month: Int,
        val room: Room
    ) {
        @Serializable
        data class Room(
            val id: String,
            val name: String

        )
    }
}



