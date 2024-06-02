package com.eespinor.lightreading.setting.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eespinor.lightreading.R
import com.eespinor.lightreading.common.BarState
import com.eespinor.lightreading.common.UiEvent
import com.eespinor.lightreading.reading.presentation.add.components.TextEntry

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onComposing: (BarState) -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: SettingViewModel = hiltViewModel(),

    ) {
    val state = viewModel.state
    val nameTitle = stringResource(id = R.string.settings)
    val settingSaved = stringResource(id = R.string.setting_saved)
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        onComposing(BarState(title = nameTitle))

        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> {
                    snackbarHostState.showSnackbar(
                        message = settingSaved
                    )
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
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
                .padding(bottom = 60.dp), // Padding to prevent content from overlapping with the button

            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Top
            // .padding(16.dp)
        ) {

            TextEntry(
                label = R.string.measure,
                textValue = state.reading,
                isError = state.readingError != null,
                errorMessage = state.readingError,
                keyboardType = KeyboardType.Decimal,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(),
            ) {
                viewModel.onEvent(SettingEvent.OnReadingChanged(it))
                viewModel.onEvent(SettingEvent.OnCalculatePrice)
            }

            TextEntry(
                label = R.string.total_price,
                textValue = state.total,
                isError = state.totalError != null,
                errorMessage = state.totalError,
                keyboardType = KeyboardType.Decimal,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth()
            ) {
                viewModel.onEvent(SettingEvent.OnTotalChanged(it))
                viewModel.onEvent(SettingEvent.OnCalculatePrice)

            }

            TextEntry(
                label = R.string.price_kwh,
                textValue = state.priceKwh,
                isError = state.priceKwhError != null,
                errorMessage = state.priceKwhError,
                keyboardType = KeyboardType.Decimal,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth()
            ) {
                viewModel.onEvent(SettingEvent.OnPriceKwhChanged(it))
            }
        }


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            shape = RoundedCornerShape(5.dp), // Change the value to modify corner radius

            onClick = { viewModel.onEvent(SettingEvent.OnSubmit) },
            //contentPadding = ButtonDefaults.ButtonWithIconContentPadding,

        ) {

            Text(text = stringResource(id = R.string.save))
        }
    }
}