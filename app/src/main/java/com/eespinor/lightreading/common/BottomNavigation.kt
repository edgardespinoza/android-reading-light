package com.eespinor.lightreading.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.eespinor.lightreading.R
import com.eespinor.lightreading.reading.presentation.Screen

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: (navController : NavController)->Unit,
    val badgeCount: Int? = null,
)

@Composable
fun getListOfItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            title = stringResource(id = R.string.home),
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            route = {navController ->
                navController.navigate(Screen.ReadingListScreen.route)
            }
        ),

        BottomNavigationItem(
            title = stringResource(id = R.string.settings),
            selectedIcon = Icons.Filled.Settings,
            unSelectedIcon = Icons.Outlined.Settings,
            route = {navController ->
                navController.navigate(Screen.SettingsScreen)

            }
        ),
    )
}

@Composable
fun BottomNavigation(
    navController : NavController
) {

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar {
        getListOfItems().forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    item.route(navController)
                },
                label = {
                    Text(text = item.title)
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
