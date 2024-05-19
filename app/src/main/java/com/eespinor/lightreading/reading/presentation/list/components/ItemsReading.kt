package com.eespinor.lightreading.reading.presentation.list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.eespinor.lightreading.reading.domain.reading.model.Reading
import com.eespinor.lightreading.reading.presentation.list.ReadingListState

@Composable
fun ItemsReading(
    readings: List<Reading>,
    error: String = "",
    isLoading: Boolean = false,
    onItemClick: (Reading) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = readings, key = { item -> item.id }) { item ->
                ItemReading(reading = item, onItemClick  )
            }
        }
        if (error.isNotBlank()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }
}