package com.eespinor.lightreading.reading.presentation.add.components

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun TextEntry(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    textValue: String,
    keyboardType: KeyboardType,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    isError: Boolean = false,
    @StringRes errorMessage: Int? = null,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = textValue,
        onValueChange = {
            if (keyboardType == KeyboardType.Number || keyboardType == KeyboardType.Decimal) {
                if (it.isEmpty()) {
                    onValueChange(it)
                } else {
                    val result = when (it.toDoubleOrNull()) {
                        null -> textValue
                        else -> it
                    }
                    onValueChange(result)
                }
            } else {
                onValueChange(it)
            }

        },
        label = {
            Text(text = if (isError) "${stringResource(label)}*" else stringResource(label))
        },
        modifier = modifier,
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        supportingText = {
            errorMessage?.let {
                Text(
                    text = stringResource(id = it) ,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )

}
