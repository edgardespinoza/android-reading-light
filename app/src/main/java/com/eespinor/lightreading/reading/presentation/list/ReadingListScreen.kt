package com.eespinor.lightreading.reading.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eespinor.lightreading.R
import com.eespinor.lightreading.common.ScaffoldViewState
import com.eespinor.lightreading.reading.presentation.Screen
import com.eespinor.lightreading.reading.presentation.list.components.ItemsReading
import com.eespinor.lightreading.reading.presentation.list.components.MonthYearPicker

@Composable
fun ReadingListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ReadingListViewModel = hiltViewModel(),
    onScaffoldViewState: (ScaffoldViewState) -> Unit,
    onFabVisible: (Boolean) -> Unit,
) {
    val state = viewModel.state
    val context = LocalContext.current
    val graphicsLayer = rememberGraphicsLayer()

    val nameTitle = stringResource(id = R.string.readings)

    LaunchedEffect(Unit) {
        onScaffoldViewState(
            ScaffoldViewState(
                topAppBarTitle = nameTitle,
                fabText = R.string.add,
                fabIcon = Icons.Filled.Add,
                onFabClick = {
                    navController.navigate(Screen.ReadingAddScreen.route + "/${state.month}/${state.year}")
                },
                isBottomBarVisible = true,
                onProcessData = {
                    viewModel.onEvent(ReadingListEvent.OnShareData(context, graphicsLayer))

                }
            )
        )
    }

    val lazyListState = rememberLazyListState()


    val expandedFab by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ReadingListEvent.OnGetReadings)

    }

    LaunchedEffect(key1 = expandedFab) {
        onFabVisible((expandedFab))
    }


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
                        Screen.ReadingEditScreen(
                            id = reading.id,
                            month = state.month,
                            year = state.year,
                            measurePrevious = reading.measurePrevious.toString(),
                            differenceMeasure = reading.differenceMeasure.toString(),
                            amountPaid = reading.amountPaid.toString(),
                            measure = reading.measure.toString(),
                            room = Screen.ReadingEditScreen.RoomEditData(
                                reading.room.id,
                                reading.room.name
                            )
                        )
                    )
                },
                onRefresh = {
                    viewModel.onEvent(ReadingListEvent.OnRefreshing)
                },
                lazyListState = lazyListState,
                graphicsLayer = graphicsLayer,
            )
        }


    }
}
