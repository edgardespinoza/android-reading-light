package com.eespinor.lightreading.reading.presentation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
sealed class Screen(val route: String) {
    object ReadingListScreen : Screen("reading_list_screen")
    object ReadingAddScreen : Screen("reading_add_screen")

    @Serializable
    data object SettingsScreen
}


@Serializable
data class ReadingEditScreen(
    val id: String,
    val measure: String,
    val year: Int,
    val month: Int,
    val room: RoomEditData,
) {
    @Serializable
    @Parcelize
    data class RoomEditData(
        val id: String,
        val name: String,
    ) : Parcelable
}

inline fun <reified T : Parcelable> createCustomNavType(): NavType<T> {
    return object : NavType<T>(isNullableAllowed = false) {
        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putParcelable(key, value)
        }

        override fun get(bundle: Bundle, key: String): T? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, T::class.java) as? T
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelable<T>(key)
            }
        }

        override fun parseValue(value: String): T {
            return Json.decodeFromString(value)
        }

        override fun serializeAsValue(value: T): String {
            return Json.encodeToString(value)
        }
    }
}

val RoomEditDataNavType = createCustomNavType<ReadingEditScreen.RoomEditData>()

