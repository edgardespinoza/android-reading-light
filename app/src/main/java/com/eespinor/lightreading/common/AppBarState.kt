package com.eespinor.lightreading.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver


data class BarState(val title: String="")

val BarStateSaver = listSaver<BarState, Any>(
    save = { listOf(it.title) },
    restore = { BarState(it[0] as String) }
)