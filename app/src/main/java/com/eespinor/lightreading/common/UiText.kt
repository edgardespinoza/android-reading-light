package com.eespinor.lightreading.common

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

sealed class UiText {
    data class DynamicString(val text: String): UiText()
    data class StringResource(val resId: Int): UiText()

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> text
            is StringResource -> context.getString(resId)
        }
    }
}