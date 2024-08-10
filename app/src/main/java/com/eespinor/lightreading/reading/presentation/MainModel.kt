package com.eespinor.lightreading.reading.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eespinor.lightreading.setting.data.repository.SettingRepositoryLocal
import com.eespinor.lightreading.setting.domain.usecase.AddSetting
import com.eespinor.lightreading.setting.domain.usecase.GetSettingRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val getSettingUseCase: GetSettingRemote,
    val addSetting: AddSetting,

    ) : ViewModel() {

    var isReady by mutableStateOf(false)
        private set


    private var getSettingJob: Job? = null


    init {
        viewModelScope.launch {
            delay(2000L)
            isReady = true
        }

        getSetting()

    }


    private fun getSetting() {
        getSettingJob?.cancel()
        getSettingJob = viewModelScope.launch {
            val setting = getSettingUseCase()
            setting?.let {
                addSetting(setting)
            }
        }
    }
}