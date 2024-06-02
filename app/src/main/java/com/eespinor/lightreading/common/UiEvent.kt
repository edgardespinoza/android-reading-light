package com.eespinor.lightreading.common

sealed class UiEvent {
    object Success: UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
}
