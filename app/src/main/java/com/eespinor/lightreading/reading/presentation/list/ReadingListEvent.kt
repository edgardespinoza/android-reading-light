package com.eespinor.lightreading.reading.presentation.list

sealed class ReadingListEvent {
    data class OnYearChanged(val year : Int): ReadingListEvent()
    data class OnMonthChanged(val month: Int): ReadingListEvent()
    data object OnGetReadings: ReadingListEvent()
    object OnRefreshing: ReadingListEvent()
}
