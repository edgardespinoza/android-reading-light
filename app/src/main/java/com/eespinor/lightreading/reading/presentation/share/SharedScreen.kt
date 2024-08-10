package com.eespinor.lightreading.reading.presentation.share

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eespinor.lightreading.R
import com.eespinor.lightreading.common.ScaffoldViewState
import com.eespinor.lightreading.reading.presentation.Screen
import com.eespinor.lightreading.reading.presentation.add.ReadingAddEvent
import com.eespinor.lightreading.reading.presentation.add.ReadingAddViewModel
import com.eespinor.lightreading.reading.presentation.ui.theme.LightReadingTheme

@Composable
fun SharedScreen(
    modifier: Modifier = Modifier,
    item: Screen.SharedScreen? = null,
    viewModel: SharedViewModel = hiltViewModel(),
    onScaffoldViewState: (ScaffoldViewState) -> Unit,
) {
    val graphicsLayer = rememberGraphicsLayer()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        onScaffoldViewState(
            ScaffoldViewState(
                isBottomBarVisible = false,
                isTopBarVisible = false,
                onProcessData = {
                    viewModel.onEvent(SharedEvent.OnShareData(context, graphicsLayer))

                }
            )
        )
    }

    Box(modifier = Modifier.fillMaxWidth()) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .drawWithContent {
                    // call record to capture the content in the graphics layer
                    graphicsLayer.record {
                        // draw the contents of the composable into the graphics layer
                        this@drawWithContent.drawContent()
                    }
                    // draw the graphics layer on the visible canvas
                    drawLayer(graphicsLayer)

                }
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "${stringResource(id = R.string.room)} : ",
                        style = MaterialTheme.typography.headlineLarge
                    )

                }
                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        text = "${item?.roomName}",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "${stringResource(id = R.string.price_paid_shared)} : ",
                        style = MaterialTheme.typography.headlineLarge
                    )

                }
                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        text = String.format(
                            stringResource(id = R.string.amount),
                            "${item?.amountPaid}"
                        ),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = "${stringResource(id = R.string.before)} : ")

                }
                Column(modifier = Modifier.weight(1f)) {

                    Text(text = "${item?.measurePrevious}")
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = "${stringResource(id = R.string.difference)} : ")

                }
                Column(modifier = Modifier.weight(1f)) {

                    Text(text = "${item?.differenceMeasure}")
                }
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = "${stringResource(id = R.string.month)} : ")

                }
                Column(modifier = Modifier.weight(1f)) {

                    Text(text = "${item?.month}")
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = "${stringResource(id = R.string.year)} : ")

                }
                Column(modifier = Modifier.weight(1f)) {

                    Text(text = "${item?.year}")
                }
            }

        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(5.dp), // Change the value to modify corner radius
            onClick = { viewModel.onEvent(SharedEvent.OnShareData(context, graphicsLayer)) },
        ) {
            Text(text = stringResource(id = R.string.share))
        }
    }
}
