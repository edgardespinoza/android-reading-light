package com.eespinor.lightreading.reading.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eespinor.lightreading.reading.presentation.Screen
import com.eespinor.lightreading.reading.presentation.list.components.ItemsReading
import com.eespinor.lightreading.reading.presentation.list.components.MonthYearPicker
import java.time.LocalDate

@Composable
fun ReadingListScreen(
    navController: NavController, viewModel: ReadingListViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize()) {


        MonthYearPicker(state.month, state.year,
            onMonthChange = {
                viewModel.onEvent(ReadingListEvent.OnMonthChanged(it))
            }, onYearChange = {
                viewModel.onEvent(ReadingListEvent.OnYearChanged(it))
            })

        Spacer(modifier = Modifier.height(16.dp))
        ItemsReading(state.readings, state.error, state.isLoading, onItemClick = {
            navController.navigate(Screen.ReadingAddScreen.route + "/${it.id}")
        })
    }
}
