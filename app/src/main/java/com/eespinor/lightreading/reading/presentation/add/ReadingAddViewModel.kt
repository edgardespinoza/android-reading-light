package com.eespinor.lightreading.reading.presentation.add

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eespinor.lightreading.R
import com.eespinor.lightreading.common.Constants
import com.eespinor.lightreading.common.Resource
import com.eespinor.lightreading.common.UiText
import com.eespinor.lightreading.common.ValidationResult
import com.eespinor.lightreading.reading.domain.reading.model.ReadingType
import com.eespinor.lightreading.reading.domain.reading.model.calculateAmountPaid
import com.eespinor.lightreading.reading.domain.reading.model.calculateDifferenceMeasure
import com.eespinor.lightreading.reading.domain.reading.usecase.AddReading
import com.eespinor.lightreading.reading.domain.reading.usecase.ValidateMeasure
import com.eespinor.lightreading.reading.domain.reading.usecase.ValidateMonth
import com.eespinor.lightreading.reading.domain.reading.usecase.ValidateRoom
import com.eespinor.lightreading.reading.domain.reading.usecase.ValidateYear
import com.eespinor.lightreading.reading.domain.room.usecase.GetRooms
import com.eespinor.lightreading.reading.presentation.add.components.UiEvent
import com.eespinor.lightreading.setting.domain.model.Setting
import com.eespinor.lightreading.setting.domain.usecase.GetSetting
import com.eespinor.lightreading.util.saveBitmapToFile
import com.eespinor.lightreading.util.shareScreenshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingAddViewModel @Inject constructor(
    private val getRoomUseCase: GetRooms,
    private val addReadingUseCase: AddReading,
    savedStateHandle: SavedStateHandle,
    private val validateMeasureUseCase: ValidateMeasure,
    private val validateRoomUseCase: ValidateRoom,
    private val validateYearUseCase: ValidateYear,
    private val validateMonthUseCase: ValidateMonth,
    private val getSettingUseCase: GetSetting,

    ) : ViewModel() {

    var state by mutableStateOf(ReadingAddState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var setting: Setting? = null


    private var searchRoomJob: Job? = null

    private var saveReadingJob: Job? = null

    private var getSettingJob: Job? = null

    private var shareDateJob: Job? = null

    init {
        getRooms()

        getSetting()

        savedStateHandle.get<Int>(Constants.PARAM_MONTH)?.let { month ->
            state = state.copy(month = month)
        }

        savedStateHandle.get<Int>(Constants.PARAM_YEAR)?.let { year ->
            state = state.copy(year = year)
        }

    }

    fun onEvent(event: ReadingAddEvent) {
        when (event) {
            is ReadingAddEvent.OnMeasureChanged -> {
                val differenceMeasure = calculateDifferenceMeasure(
                    event.measure.toDoubleOrNull() ?: 0.0,
                    state.measurePrevious?.toDoubleOrNull()
                )
                state = state.copy(
                    measure = event.measure,
                    amountPaid = calculateAmountPaid(setting?.priceKwh, differenceMeasure).toString(),
                    differenceMeasure = differenceMeasure.toString()
                )
            }

            is ReadingAddEvent.OnMonthChanged -> {
                state = state.copy(month = event.month)
            }

            is ReadingAddEvent.OnRoomChanged -> {
                state = state.copy(roomId = event.roomId)
            }

            is ReadingAddEvent.OnYearChanged -> {
                state = state.copy(year = event.year)
            }

            is ReadingAddEvent.OnSubmit -> {
                saveReading()
            }

            is ReadingAddEvent.OnLoadData -> {
                state = state.copy(
                    measurePrevious = event.measurePrevious,
                    differenceMeasure = event.differenceMeasure,
                    amountPaid = event.amountPaid
                )
            }

            is ReadingAddEvent.OnShareData -> {
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


    private fun saveReading() {

        val measureResult = validateMeasureUseCase(state.measure)
        val roomResult = validateRoomUseCase(state.roomId)
        val yearResult = validateYearUseCase(state.year)
        val monthResult = validateMonthUseCase(state.month)
        val hasError =
            listOf(measureResult, roomResult, yearResult, monthResult).any { !it.successful }

        if (hasError) {
            state = state.copy(
                measureError = getMessage(measureResult),
                roomError = getMessage(roomResult),
                yearError = getMessage(yearResult),
                monthError = getMessage(monthResult)
            )
            return
        }

        state =
            state.copy(measureError = null, roomError = null, yearError = null, monthError = null)

        saveReadingJob?.cancel()

        saveReadingJob = viewModelScope.launch {
            addReadingUseCase(
                state.measure.toDouble(), state.year, state.month, state.roomId
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiEvent.send(UiEvent.Success)
                    }

                    is Resource.Error -> {
                        _uiEvent.send(
                            UiEvent.ShowSnackbar(
                                UiText.DynamicString(result.message ?: "")
                            )
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }


    private fun getRooms() {
        searchRoomJob?.cancel()
        searchRoomJob = viewModelScope.launch {
            getRoomUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(rooms = result.data ?: emptyList())
                        _uiEvent.send(UiEvent.RoomCompleted)
                    }

                    is Resource.Error -> {
                        state = state.copy(isErrorGetRooms = true)
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

    private fun getSetting() {
        getSettingJob?.cancel()
        getSettingJob = viewModelScope.launch {
            if (setting == null) {
                setting = getSettingUseCase()
            }
        }
    }
}

private fun getMessage(validationResult: ValidationResult<ReadingType>): Int? {
    if (validationResult.successful) {
        return null
    }

    return when (validationResult.type) {
        ReadingType.MeasureEmpty -> {
            R.string.measure_empty
        }

        ReadingType.MeasureIsNotNumber -> {
            R.string.measure_must_be_number
        }

        ReadingType.RoomEmpty -> {
            R.string.room_empty
        }

        ReadingType.MonthInvalid -> {
            R.string.month_invalid
        }

        ReadingType.YearInvalid -> {
            R.string.year_invalid
        }

        else -> null
    }

}