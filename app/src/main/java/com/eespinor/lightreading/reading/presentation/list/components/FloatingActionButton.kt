package com.eespinor.lightreading.reading.presentation.list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    lazyListState: LazyListState,
) {
    val expandedFab by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }
    val fabAlpha by animateFloatAsState(targetValue = if (expandedFab) 1f else 0f, label = "")

    ExtendedFloatingActionButton(
        modifier = modifier
            .alpha(fabAlpha),
        onClick = onClick,
        icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
        text = { Text(text = "Add") },

        )

}