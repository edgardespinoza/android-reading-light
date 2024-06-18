package com.eespinor.lightreading.reading.presentation.list

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eespinor.lightreading.common.Resource
import com.eespinor.lightreading.reading.domain.reading.usecase.GetReadings
import com.eespinor.lightreading.setting.domain.model.Setting
import com.eespinor.lightreading.setting.domain.usecase.GetSetting
import com.eespinor.lightreading.util.saveBitmapToFile
import com.eespinor.lightreading.util.shareScreenshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingListViewModel @Inject constructor(
    private val getReadingUseCase: GetReadings,
    private val getSettingUseCase: GetSetting,
) : ViewModel() {

    var state by mutableStateOf(ReadingListState())
        private set

    private var searchReadingJob: Job? = null
    private var shareDateJob: Job? = null

    private var setting: Setting? = null

    fun onEvent(event: ReadingListEvent) {
        when (event) {
            is ReadingListEvent.OnYearChanged -> {
                state = state.copy(year = event.year)
                getReadings()
            }

            is ReadingListEvent.OnMonthChanged -> {
                state = state.copy(month = event.month)
                getReadings()
            }

            is ReadingListEvent.OnRefreshing -> {
                getRefreshing()
            }

            is ReadingListEvent.OnGetReadings -> {
                getReadings()
            }

            is ReadingListEvent.OnShareData -> {
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


    private fun getReadings(
        month: Int = state.month, year: Int = state.year,
        changeLoading: (Boolean) -> Unit = {
            state = state.copy(isLoading = it)
        },
    ) {
        searchReadingJob?.cancel()
        searchReadingJob = viewModelScope.launch {

            if (setting == null) {
                setting = getSettingUseCase()
            }

            getReadingUseCase(month, year, setting).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(readings = result.data ?: emptyList(), error = "")
                    }

                    is Resource.Error -> {
                        state = state.copy(error = result.message ?: "An unexpected error occurred")
                    }

                    is Resource.Loading -> {
                        changeLoading(result.isLoading)
                    }
                }
            }
        }
    }


    private fun getRefreshing() {
        getReadings { changeStatus ->
            state = state.copy(isRefreshing = changeStatus)
        }
    }


}