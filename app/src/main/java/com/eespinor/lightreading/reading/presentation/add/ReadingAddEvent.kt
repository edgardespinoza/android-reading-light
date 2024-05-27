package com.eespinor.lightreading.reading.presentation.add

import com.eespinor.lightreading.reading.domain.reading.model.Reading
import com.eespinor.lightreading.reading.presentation.list.ReadingListEvent

sealed class ReadingAddEvent{
    object OnSubmit: ReadingAddEvent()
    data class OnMeasureChanged(val measure: String): ReadingAddEvent()
    data class OnRoomChanged(val roomId: String): ReadingAddEvent()
    data class OnMonthChanged(val month: Int): ReadingAddEvent()
    data class OnYearChanged(val year: Int): ReadingAddEvent()
}
