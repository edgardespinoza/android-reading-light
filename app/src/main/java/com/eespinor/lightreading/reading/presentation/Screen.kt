package com.eespinor.lightreading.reading.presentation

import kotlinx.serialization.Serializable


sealed class Screen(val route: String) {
    object ReadingListScreen : Screen("reading_list_screen")
    object ReadingAddScreen : Screen("reading_add_screen")
}




