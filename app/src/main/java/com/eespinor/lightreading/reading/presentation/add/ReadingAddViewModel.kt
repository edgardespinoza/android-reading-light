package com.eespinor.lightreading.reading.presentation.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eespinor.lightreading.common.Constants
import com.eespinor.lightreading.common.Resource
import com.eespinor.lightreading.reading.domain.reading.usecase.AddReading
import com.eespinor.lightreading.reading.domain.reading.usecase.GetReading
import com.eespinor.lightreading.reading.domain.reading.usecase.UpdateReading
import com.eespinor.lightreading.reading.domain.reading.usecase.ValidateMeasure
import com.eespinor.lightreading.reading.domain.reading.usecase.ValidateMonth
import com.eespinor.lightreading.reading.domain.reading.usecase.ValidateRoom
import com.eespinor.lightreading.reading.domain.reading.usecase.ValidateYear
import com.eespinor.lightreading.reading.domain.room.usecase.GetRooms
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
    private val validateMonthUseCase: ValidateMonth
) : ViewModel() {

    var state by mutableStateOf(ReadingAddState())
        private set

    private var searchRoomJob: Job? = null

    private var saveReadingJob: Job? = null

    private var getReadingJob: Job? = null


    init {
        getRooms()

        savedStateHandle.get<String>(Constants.PARAM_READING_ID)?.let { id ->
            getReading(id)
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
        val hasError = listOf(measureResult, roomResult, yearResult, monthResult).any { !it.successful }

        if (hasError) {
            state = state.copy(
                measureError = measureResult.successful,
                roomError = roomResult.successful,
                yearError = yearResult.successful,
                monthError = monthResult.successful
            )
            return
        }
        saveReadingJob?.cancel()
        saveReadingJob = viewModelScope.launch {
            addReadingUseCase(
                state.measure,
                state.year,
                state.month,
                state.roomId
            ).collect { result ->
                state = when (result) {
                    is Resource.Success -> {
                        state.copy(isSuccessfullyRegistered = true)
                    }

                    is Resource.Error -> {
                        state.copy(isErrorRegister = true)
                    }

                    is Resource.Loading -> {
                        state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }


    private fun getRooms() {
        searchRoomJob?.cancel()
        searchRoomJob = viewModelScope.launch {
            getRoomUseCase().collect { result ->
                state = when (result) {
                    is Resource.Success -> {
                        state.copy(rooms = result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        state.copy(isErrorGetRooms =true)
                    }

                    is Resource.Loading -> {
                        state.copy(isLoading = result.isLoading)
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
                                measure = reading.measure,
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
