package com.eespinor.lightreading.reading.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eespinor.lightreading.R
import com.eespinor.lightreading.common.BarState
import com.eespinor.lightreading.reading.presentation.ReadingEditScreen
import com.eespinor.lightreading.reading.presentation.Screen
import com.eespinor.lightreading.reading.presentation.list.components.FloatingActionButton
import com.eespinor.lightreading.reading.presentation.list.components.ItemsReading
import com.eespinor.lightreading.reading.presentation.list.components.MonthYearPicker

@Composable
fun ReadingListScreen(
    onComposing: (BarState) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ReadingListViewModel = hiltViewModel(),
) {
    val state = viewModel.state

    val nameTitle = stringResource(id = R.string.readings)

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ReadingListEvent.OnGetReadings)

        onComposing(BarState(title = nameTitle))
    }

    val lazyListState = rememberLazyListState()



    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {

            MonthYearPicker(
                month = state.month,
                year = state.year,
                onMonthChange = {
                    viewModel.onEvent(ReadingListEvent.OnMonthChanged(it))
                }, onYearChange = {
                    viewModel.onEvent(ReadingListEvent.OnYearChanged(it))
                })

            ItemsReading(
                items = state.readings,
                error = state.error,
                isLoading = state.isLoading,
                isRefreshing = state.isRefreshing,
                onItemClick = { reading ->
                    navController.navigate(
                        ReadingEditScreen(
                            id = reading.id,
                            month = state.month,
                            year = state.year,
                            measure = reading.measure.toString(),
                            room = ReadingEditScreen.RoomEditData(
                                reading.room.id,
                                reading.room.name
                            )
                        )
                    )
                },
                onRefresh = {
                    viewModel.onEvent(ReadingListEvent.OnRefreshing)
                },
                lazyListState = lazyListState
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                navController.navigate(Screen.ReadingAddScreen.route + "/${state.month}/${state.year}")
            },
            lazyListState = lazyListState
        )
    }
}
