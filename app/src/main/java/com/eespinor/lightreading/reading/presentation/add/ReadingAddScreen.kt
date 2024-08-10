package com.eespinor.lightreading.reading.presentation.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eespinor.lightreading.R
import com.eespinor.lightreading.common.ScaffoldViewState
import com.eespinor.lightreading.reading.presentation.Screen
import com.eespinor.lightreading.reading.presentation.add.components.RoomPicker
import com.eespinor.lightreading.reading.presentation.add.components.TextEntry
import com.eespinor.lightreading.reading.presentation.add.components.UiEvent
import com.eespinor.lightreading.reading.presentation.list.ReadingListEvent
import com.eespinor.lightreading.reading.presentation.list.components.MonthYearPicker
import java.time.Month

@Composable
fun ReadingAddScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    viewModel: ReadingAddViewModel = hiltViewModel(),
    item: Screen.ReadingEditScreen? = null,
    onScaffoldViewState: (ScaffoldViewState) -> Unit,
) {

    val state = viewModel.state
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val nameTitle = stringResource(id = R.string.room_item)
    val graphicsLayer = rememberGraphicsLayer()

    LaunchedEffect(key1= state) {
        onScaffoldViewState(
            ScaffoldViewState(
                topAppBarTitle = String.format(nameTitle, item?.room?.name ?: ""),
                isBottomBarVisible = true,
                onProcessData = {
                    navController.navigate(
                        Screen.SharedScreen(
                            month = Month.of(state.month).name,
                            year = state.year,
                            measurePrevious = state.measurePrevious,
                            differenceMeasure = state.differenceMeasure,
                            amountPaid = state.amountPaid,
                            measure = state.measure,
                            roomName = state.rooms.find { it.id == state.roomId }?.name ?: "",
                        )
                    )
                }
            )
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> {
                    navController.navigateUp()
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }

                is UiEvent.RoomCompleted -> {
                    item?.let {
                        viewModel.onEvent(ReadingAddEvent.OnYearChanged(it.year))
                        viewModel.onEvent(ReadingAddEvent.OnMonthChanged(it.month))
                        viewModel.onEvent(ReadingAddEvent.OnMeasureChanged(it.measure))
                        viewModel.onEvent(ReadingAddEvent.OnRoomChanged(it.room.id))
                        viewModel.onEvent(
                            ReadingAddEvent.OnLoadData(
                                it.measurePrevious,
                                it.differenceMeasure,
                                it.amountPaid
                            )
                        )
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                //.safeDrawingPadding()
                .padding(bottom = 60.dp)
                .drawWithContent {
                    // call record to capture the content in the graphics layer
                    graphicsLayer.record {
                        // draw the contents of the composable into the graphics layer
                        this@drawWithContent.drawContent()
                    }
                    // draw the graphics layer on the visible canvas
                    drawLayer(graphicsLayer)

                },
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Top
            // .padding(16.dp)
        ) {
            MonthYearPicker(modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
                month = state.month,
                year = state.year,
                isErrorMonth = state.monthError != null,
                errorMonthMessage = state.monthError,
                isErrorYear = state.yearError != null,
                errorYearMessage = state.yearError,
                onMonthChange = {
                    viewModel.onEvent(ReadingAddEvent.OnMonthChanged(it))
                },
                onYearChange = {
                    viewModel.onEvent(ReadingAddEvent.OnYearChanged(it))
                })


            RoomPicker(modifier = Modifier
                //  .padding(bottom = 32.dp)
                .fillMaxWidth(),
                label = stringResource(id = R.string.room),
                rooms = state.rooms,
                isError = state.roomError != null,
                errorMessage = state.roomError,
                idRoom = state.roomId,
                onRoomChange = {
                    viewModel.onEvent(ReadingAddEvent.OnRoomChanged(it))
                    focusManager.moveFocus(FocusDirection.Down)
                })

            Spacer(modifier = Modifier.height(8.dp))

            TextEntry(
                label = R.string.measure,
                textValue = state.measure,
                isError = state.measureError != null,
                errorMessage = state.measureError,
                keyboardType = KeyboardType.Decimal,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                viewModel.onEvent(ReadingAddEvent.OnMeasureChanged(it))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${stringResource(id = R.string.before)}: ${state.measurePrevious}",
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = "${stringResource(id = R.string.difference)}: ${state.differenceMeasure} ",
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.End,
                )

            }

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = String.format(
                        stringResource(id = R.string.price_paid),
                        state.amountPaid
                    ),
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            shape = RoundedCornerShape(5.dp), // Change the value to modify corner radius
            onClick = { viewModel.onEvent(ReadingAddEvent.OnSubmit) },
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

