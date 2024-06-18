package com.eespinor.lightreading.reading.presentation.list

import android.content.Context
import androidx.compose.ui.graphics.layer.GraphicsLayer

sealed class ReadingListEvent {
    data class OnYearChanged(val year: Int) : ReadingListEvent()
    data class OnMonthChanged(val month: Int) : ReadingListEvent()
    data class  OnShareData(val context: Context, val graphicsLayer: GraphicsLayer) : ReadingListEvent()

    data object OnGetReadings : ReadingListEvent()
    data object OnRefreshing : ReadingListEvent()

}
