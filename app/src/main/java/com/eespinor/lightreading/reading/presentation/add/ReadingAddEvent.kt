package com.eespinor.lightreading.reading.presentation.add

import android.content.Context
import androidx.compose.ui.graphics.layer.GraphicsLayer
import com.eespinor.lightreading.reading.domain.reading.model.Reading
import com.eespinor.lightreading.reading.presentation.list.ReadingListEvent

sealed class ReadingAddEvent {
    data object OnSubmit : ReadingAddEvent()
    data class OnMeasureChanged(val measure: String) : ReadingAddEvent()
    data class OnRoomChanged(val roomId: String) : ReadingAddEvent()
    data class OnMonthChanged(val month: Int) : ReadingAddEvent()
    data class OnYearChanged(val year: Int) : ReadingAddEvent()
    data class  OnShareData(val context: Context, val graphicsLayer: GraphicsLayer) : ReadingAddEvent()

    data class OnLoadData(
        val measurePrevious: String?,
        val differenceMeasure: String?,
        val amountPaid: String?,
    ) : ReadingAddEvent()
}
