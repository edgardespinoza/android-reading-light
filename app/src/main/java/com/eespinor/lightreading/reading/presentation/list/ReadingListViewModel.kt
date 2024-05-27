package com.eespinor.lightreading.reading.presentation.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eespinor.lightreading.common.Resource
import com.eespinor.lightreading.reading.domain.reading.usecase.GetReadings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingListViewModel @Inject constructor(
    private val getReadingUseCase: GetReadings,
) : ViewModel() {

    var state by mutableStateOf(ReadingListState())
        private set

    private var searchReadingJob: Job? = null

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
        }

    }


    private fun getReadings(month: Int = state.month, year: Int = state.year, changeLoading: (Boolean)->Unit={
        state = state.copy(isLoading = it)
    }) {
        searchReadingJob?.cancel()
        searchReadingJob = viewModelScope.launch {
            getReadingUseCase(month, year).collect { result ->
                 when (result) {
                    is Resource.Success -> {
                       state = state.copy(readings = result.data ?: emptyList())
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
        getReadings{changeStatus ->
            state = state.copy(isRefreshing = changeStatus)
        }
    }
}