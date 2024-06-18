package com.eespinor.lightreading.common

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eespinor.lightreading.R
import com.eespinor.lightreading.reading.presentation.Screen

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: String,
    val navigate: (navController: NavController) -> Unit = {},
    val badgeCount: Int? = null,
    val action: (scaffoldViewState: ScaffoldViewState) -> Unit = {},
)

@Composable
fun getListOfItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            title = stringResource(id = R.string.home),
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            route = Screen.ReadingListScreen::class.qualifiedName
                ?: Screen.ReadingListScreen::class.java.name,
            navigate = { navController ->
                navController.navigate(Screen.ReadingListScreen)
            }
        ),

        BottomNavigationItem(
            title = stringResource(id = R.string.share),
            selectedIcon = Icons.Filled.Share,
            unSelectedIcon = Icons.Outlined.Share,
            route = "",
            action = { scaffoldViewState ->
                scaffoldViewState.onProcessData?.invoke()
            }
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.settings),
            selectedIcon = Icons.Filled.Settings,
            unSelectedIcon = Icons.Outlined.Settings,
            route = Screen.SettingsScreen::class.qualifiedName
                ?: Screen.SettingsScreen::class.java.name,
            navigate = { navController ->
                navController.navigate(Screen.SettingsScreen)
            }
        ),
    )
}

@Composable
fun BottomNavigation(
    navController: NavController,
    scaffoldViewState: ScaffoldViewState
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val context = LocalContext.current
    val view = LocalView.current

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar (modifier = Modifier.fillMaxWidth()){
        getListOfItems().forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    selectedItemIndex = index
                    item.navigate(navController)
                    item.action(scaffoldViewState)
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                alwaysShowLabel = true,
                icon = {
                    BadgedBox(badge = {
                        item.badgeCount?.let {
                            Badge {
                                Text(text = item.badgeCount.toString())
                            }
                        }


                    }) {
                        Icon(
                            imageVector = if (index == selectedItemIndex)
                                item.selectedIcon else item.unSelectedIcon,
                            contentDescription = item.title
                        )
                    }
                })
        }
    }


}
