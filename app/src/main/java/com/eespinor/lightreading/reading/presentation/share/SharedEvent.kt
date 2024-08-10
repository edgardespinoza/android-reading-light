package com.eespinor.lightreading.reading.presentation.share

import android.content.Context
import androidx.compose.ui.graphics.layer.GraphicsLayer

sealed class SharedEvent {
    data class  OnShareData(val context: Context, val graphicsLayer: GraphicsLayer) : SharedEvent()

}
