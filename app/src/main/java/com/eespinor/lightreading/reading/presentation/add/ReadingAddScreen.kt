package com.eespinor.lightreading.reading.presentation.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eespinor.lightreading.reading.presentation.list.ReadingListEvent
import com.eespinor.lightreading.reading.presentation.list.components.MonthYearPicker

@Composable
fun ReadingAddScreen(
    navController: NavController,
    viewModel: ReadingAddViewModel = hiltViewModel()
) {

    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {
        MonthYearPicker(state.month, state.year,
            onMonthChange = {
                viewModel.onEvent(ReadingAddEvent.OnMonthChanged(it))
            }, onYearChange = {
                viewModel.onEvent(ReadingAddEvent.OnYearChanged(it))
            })


    }
}

