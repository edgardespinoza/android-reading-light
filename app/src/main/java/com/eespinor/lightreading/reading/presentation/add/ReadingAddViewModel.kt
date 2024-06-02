package com.eespinor.lightreading.reading.presentation.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eespinor.lightreading.R
import com.eespinor.lightreading.common.Constants
import com.eespinor.lightreading.common.Resource
import com.eespinor.lightreading.common.UiText
import com.eespinor.lightreading.common.ValidationResult
import com.eespinor.lightreading.reading.domain.reading.model.ReadingType
import com.eespinor.lightreading.reading.domain.reading.usecase.AddReading
import com.eespinor.lightreading.reading.domain.reading.usecase.GetReading
import com.eespinor.lightreading.reading.domain.reading.usecase.ValidateMeasure
import com.eespinor.lightreading.reading.domain.reading.usecase.ValidateMonth
import com.eespinor.lightreading.reading.domain.reading.usecase.ValidateRoom
import com.eespinor.lightreading.reading.domain.reading.usecase.ValidateYear
import com.eespinor.lightreading.reading.domain.room.usecase.GetRooms
import com.eespinor.lightreading.reading.presentation.add.components.UiEvent
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
    private val getReadingUseCase: GetReading,
    private val validateMeasureUseCase: ValidateMeasure,
    private val validateRoomUseCase: ValidateRoom,
    private val validateYearUseCase: ValidateYear,
    private val validateMonthUseCase: ValidateMonth,
) : ViewModel() {

    var state by mutableStateOf(ReadingAddState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

//    private val _eventFlow = MutableSharedFlow<UiEvent>()
//    val eventFlow = _eventFlow.asSharedFlow()


    private var searchRoomJob: Job? = null

    private var saveReadingJob: Job? = null

    private var getReadingJob: Job? = null

    init {
        getRooms()

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
                state = state.copy(measure = event.measure)
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
                roomError =    getMessage(roomResult),
                yearError =    getMessage(yearResult),
                monthError =   getMessage(monthResult)
            )
            return
        }

        state = state.copy(measureError = null, roomError = null, yearError = null, monthError = null)

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
                                UiText.DynamicString(result.message?: "" )
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
                        state= state.copy(rooms = result.data ?: emptyList())
                        _uiEvent.send(UiEvent.RoomCompleted)
                    }

                    is Resource.Error -> {
                        state= state.copy(isErrorGetRooms = true)
                    }

                    is Resource.Loading -> {
                        state=  state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

    private fun getReading(id: String) {
        getReadingJob?.cancel()
        getReadingJob = viewModelScope.launch {
            getReadingUseCase(id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { reading ->
                            state = state.copy(
                                measure = reading.measure.toString(),
                                year = reading.year,
                                month = reading.month,
                                roomId = reading.room.id
                            )
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(isErrorGetReading = true)
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
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