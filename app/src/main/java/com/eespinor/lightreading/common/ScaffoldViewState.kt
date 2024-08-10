package com.eespinor.lightreading.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class ScaffoldViewState(
    val topAppBarTitle: String? = null,
    @StringRes val fabText: Int? = null,
    val topAppBarActions: List<TopAppBarAction> = emptyList(),
    @DrawableRes val navIcon: Int? = null,
    val onNavIconClick: (() -> Unit)? = null,
    val fabIcon: ImageVector? = null,
    val onFabClick: (() -> Unit)? = null,
    val isBottomBarVisible: Boolean = true,
    val isTopBarVisible: Boolean = true,
    val onProcessData: (() -> Unit)? = null
) {

    @Immutable
    data class TopAppBarAction(
        @DrawableRes val icon: Int,
        val contentDescription: String? = null,
        val onClick: () -> Unit
    )
}