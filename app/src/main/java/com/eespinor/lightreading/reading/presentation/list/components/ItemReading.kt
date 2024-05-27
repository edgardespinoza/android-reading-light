package com.eespinor.lightreading.reading.presentation.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.eespinor.lightreading.R
import com.eespinor.lightreading.reading.domain.reading.model.Reading

@Composable
fun ItemReading(
    reading: Reading,
    onItemClick: (Reading) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(reading) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically,

                ) {
                Text(
                    text = "${stringResource(id = R.string.room)}: ${reading.room.name} ",
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${reading.measure}",
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${stringResource(id = R.string.before)}: ${reading.measurePrevious}",
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "${stringResource(id = R.string.difference)}: ${reading.measure - (reading?.measurePrevious ?: 0.0)} ",
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}
