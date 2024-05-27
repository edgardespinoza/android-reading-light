package com.eespinor.lightreading.reading.presentation.list.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eespinor.lightreading.reading.presentation.list.ReadingListState
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MonthYearPicker(
    modifier: Modifier = Modifier,
    month: Int,
    year: Int,
    onMonthChange: (Int) -> Unit,
    onYearChange: (Int) -> Unit,
    isErrorMonth: Boolean = false,
    @StringRes errorMonthMessage: Int? = null,
    isErrorYear: Boolean = false,
    @StringRes errorYearMessage: Int? = null,

    ) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(3f),
        ) {
            MonthPicker(
                month = month,
                onMonthChange = onMonthChange,
                isErrorMonth = isErrorMonth
            )

            Text(
                text = errorMonthMessage?.let { stringResource(id = it) } ?: "",
                color =  MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            )

        }
        Column(
            modifier = Modifier.weight(2f),
        ) {
            YearPicker(
                year = year,
                onYearChange = onYearChange,
                isErrorYear = isErrorYear,
            )
            Text(
                text = errorYearMessage?.let { stringResource(id = it) } ?: "",
                color =  MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthPicker(
    month: Int,
    isErrorMonth: Boolean = false,
    onMonthChange: (Int) -> Unit,
) {


    val spanishMonths =
        Month.entries.map { it.getDisplayName(TextStyle.FULL, Locale("es")).uppercase() }
    var expanded by remember { mutableStateOf(false) }
    var monthvalue by remember { mutableStateOf(month) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {



        OutlinedTextField(
            // The `menuAnchor` modifier must be passed to the text field to handle
            // expanding/collapsing the menu on click. A read-only text field has
            // the anchor type `PrimaryNotEditable`.
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = spanishMonths[monthvalue - 1],
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            isError = isErrorMonth,
            //label = { Text("Label") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            //colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            spanishMonths.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        monthvalue = index + 1
                        expanded = false
                        onMonthChange(monthvalue)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YearPicker(
    year: Int,
    isErrorYear: Boolean = false,
    onYearChange: (Int) -> Unit,
) {

    val options = listOf(LocalDate.now().year, LocalDate.now().year - 1, LocalDate.now().year - 2)
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(year) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        OutlinedTextField(
            // The `menuAnchor` modifier must be passed to the text field to handle
            // expanding/collapsing the menu on click. A read-only text field has
            // the anchor type `PrimaryNotEditable`.
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = text.toString(),
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            isError = isErrorYear,

            //label = { Text("Label") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            //  colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.toString(), style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        text = option
                        expanded = false
                        onYearChange(text)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }

}