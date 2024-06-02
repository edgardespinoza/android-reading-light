package com.eespinor.lightreading.setting.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eespinor.lightreading.R
import com.eespinor.lightreading.common.UiEvent
import com.eespinor.lightreading.common.ValidationResult
import com.eespinor.lightreading.setting.domain.model.Setting
import com.eespinor.lightreading.setting.domain.model.SettingType
import com.eespinor.lightreading.setting.domain.usecase.AddSetting
import com.eespinor.lightreading.setting.domain.usecase.CalculatePrice
import com.eespinor.lightreading.setting.domain.usecase.GetSetting
import com.eespinor.lightreading.setting.domain.usecase.ValidatePriceKwh
import com.eespinor.lightreading.setting.domain.usecase.ValidateReading
import com.eespinor.lightreading.setting.domain.usecase.ValidateTotal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getSettingUseCase: GetSetting,
    private val validatePriceKwh: ValidatePriceKwh,
    private val validateReading: ValidateReading,
    private val validateTotal: ValidateTotal,
    private val addSettingUseCase: AddSetting,
    private val calculatePrice: CalculatePrice
) : ViewModel() {
    var state by mutableStateOf(SettingState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getSettingJob: Job? = null


    init {
        getSetting()
    }

    fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.OnPriceKwhChanged -> {
                state = state.copy(
                    priceKwh = event.priceKwh
                )
            }

            is SettingEvent.OnTotalChanged -> {
                state = state.copy(
                    total = event.total
                )
            }

            is SettingEvent.OnReadingChanged -> {
                state = state.copy(
                    reading = event.reading
                )
            }

            is SettingEvent.OnSubmit  -> {
                saveSetting()
            }

            is SettingEvent.OnCalculatePrice -> {
                calculatePrice()
            }
        }
    }

    private fun calculatePrice() {
        val readingResult = validateReading(state.reading)
        val totalResult = validateTotal(state.total)
        val hasError =
            listOf(readingResult, totalResult).any { !it.successful }

        if (hasError) {
            return
        }
        val priceKwh = calculatePrice(state.total.toDouble() , state.reading.toDouble())

        state = state.copy(priceKwh = priceKwh.toString())
    }

    private fun saveSetting() {


        val priceKwhResult = validatePriceKwh(state.priceKwh)
        val readingResult = validateReading(state.reading)
        val totalResult = validateTotal(state.total)
        val hasError =
            listOf(priceKwhResult, readingResult, totalResult).any { !it.successful }

        if (hasError) {
            state = state.copy(
                readingError = getMessage(readingResult),
                totalError = getMessage(totalResult),
                priceKwhError = getMessage(priceKwhResult),
            )
            return
        }

        state =
            state.copy(readingError = null, totalError = null, priceKwhError = null)



        getSettingJob?.cancel()

        getSettingJob = viewModelScope.launch {
            addSettingUseCase(
                setting = Setting(
                    id = state.id,
                    priceKwh = state.priceKwh.toDouble(),
                    total = state.total.toDouble(),
                    reading = state.reading.toDouble()
                )
            )
            _uiEvent.send(UiEvent.Success)
        }
    }

    private fun getSetting() {
        getSettingJob?.cancel()
        getSettingJob = viewModelScope.launch {
            val setting = getSettingUseCase()
            setting?.let {
                state = state.copy(
                    id = it.id,
                    priceKwh = it.priceKwh.toString(),
                    total = it.total.toString(),
                    reading = it.reading.toString(),
                )
            }
        }


    }

    private fun getMessage(validationResult: ValidationResult<SettingType>): Int? {
        if (validationResult.successful) {
            return null
        }

        return when (validationResult.type) {
            SettingType.ReadingEmpty -> {
                R.string.measure_empty
            }

            SettingType.ReadingNotNumber -> {
                R.string.measure_must_be_number
            }

            SettingType.TotalEmpty -> {
                R.string.total_empty
            }

            SettingType.TotalNotNumber -> {
                R.string.total_must_be_number
            }

            SettingType.PriceKwhEmpty -> {
                R.string.price_kwh_empty
            }

            SettingType.PriceKwhNotNumber -> {
                R.string.price_kwh_must_be_number
            }

            else -> null

        }
    }
}