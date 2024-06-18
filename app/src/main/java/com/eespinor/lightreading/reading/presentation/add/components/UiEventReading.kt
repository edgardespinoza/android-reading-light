package com.eespinor.lightreading.reading.presentation.add.components

import com.eespinor.lightreading.common.UiText

sealed class UiEvent {
    data object Success : UiEvent()
    data object RoomCompleted : UiEvent()
    data class ShowSnackbar(val message: UiText) : UiEvent()
}
