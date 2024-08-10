package com.eespinor.lightreading.reading.presentation.share

import android.content.Context
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eespinor.lightreading.util.saveBitmapToFile
import com.eespinor.lightreading.util.shareScreenshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(

) : ViewModel() {

    private var shareDateJob: Job? = null


    fun onEvent(event: SharedEvent) {
        when (event) {

            is SharedEvent.OnShareData -> {
                shareData(event.context, event.graphicsLayer)
            }
        }
    }

    private fun shareData(context: Context, graphicsLayer: GraphicsLayer) {
        shareDateJob?.cancel()
        shareDateJob = viewModelScope.launch {
            val bitmap = graphicsLayer.toImageBitmap()

            val file = saveBitmapToFile(context, bitmap.asAndroidBitmap())

            shareScreenshot(context, file)
        }
    }


}