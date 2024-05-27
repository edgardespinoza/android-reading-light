package com.eespinor.lightreading.common

sealed class UiEvent {
    object Success: UiEvent()
    object RoomCompleted: UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
}
