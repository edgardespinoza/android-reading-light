package com.eespinor.lightreading.reading.presentation.add.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eespinor.lightreading.reading.domain.room.model.Room
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomPicker(
    modifier: Modifier = Modifier,
    label: String,
    rooms: List<Room>,
    idRoom: String,
    isError: Boolean = false,
    @StringRes errorMessage: Int? = null,
    onRoomChange: (String) -> Unit,
) {


    var expanded by remember { mutableStateOf(false) }

    var roomSelected by remember { mutableStateOf(idRoom) }

    roomSelected = idRoom

    Column(
        modifier = modifier
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            OutlinedTextField(
                // The `menuAnchor` modifier must be passed to the text field to handle
                // expanding/collapsing the menu on click. A read-only text field has
                // the anchor type `PrimaryNotEditable`.
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxSize(),
                value = getNameRoom(roomSelected, rooms),
                onValueChange = {},
                isError = isError,
                readOnly = true,
                singleLine = true,
                label = { Text(label) },
                //   colors = ExposedDropdownMenuDefaults.textFieldColors(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                rooms.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item.name, style = MaterialTheme.typography.bodyLarge) },
                        onClick = {
                            roomSelected = item.id
                            expanded = false
                            onRoomChange(roomSelected)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
        Text(
            text = errorMessage?.let { stringResource(id = it) } ?: "",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp, start = 16.dp)
        )
    }
}


private fun getNameRoom(id: String, rooms: List<Room>): String {
    if (id.isEmpty()) return ""
    return rooms.find { it.id == id }?.name ?: ""
}