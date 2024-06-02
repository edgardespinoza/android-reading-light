package com.eespinor.lightreading.reading.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.eespinor.lightreading.common.BarState
import com.eespinor.lightreading.common.BarStateSaver
import com.eespinor.lightreading.common.BottomNavigation
import com.eespinor.lightreading.reading.presentation.add.ReadingAddScreen
import com.eespinor.lightreading.reading.presentation.list.ReadingListScreen
import com.eespinor.lightreading.reading.presentation.ui.theme.LightReadingTheme
import com.eespinor.lightreading.setting.presentation.SettingScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LightReadingTheme {

                val navController = rememberNavController()

                val snackbarHostState = remember { SnackbarHostState() }

                val appBarState = rememberSaveable(stateSaver = BarStateSaver) {
                    mutableStateOf(BarState())
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {

                                Text(appBarState.value.title)
                            }
                        )
                    },
                    bottomBar = {
                        BottomNavigation(navController = navController)
                    }

                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.ReadingListScreen.route,
                    ) {
                        composable(
                            route = Screen.ReadingListScreen.route
                        ) {
                            ReadingListScreen(
                                onComposing = {
                                    appBarState.value = it
                                },
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable<ReadingEditScreen>(
                            typeMap = mapOf(typeOf<ReadingEditScreen.RoomEditData>() to RoomEditDataNavType)

                        ) { backStackEntry ->
                            val item = backStackEntry.toRoute<ReadingEditScreen>()

                            ReadingAddScreen(
                                onComposing = {
                                    appBarState.value = it
                                },
                                modifier = Modifier.padding(innerPadding),
                                snackbarHostState = snackbarHostState,
                                navController = navController,
                                item = item
                            )
                        }
                        composable(
                            route = Screen.ReadingAddScreen.route + "/{month}/{year}",
                            arguments = listOf(
                                navArgument("month") { type = NavType.IntType },
                                navArgument("year") { type = NavType.IntType }
                            )
                        ) {
                            ReadingAddScreen(
                                onComposing = {
                                    appBarState.value = it
                                },
                                modifier = Modifier.padding(innerPadding),
                                snackbarHostState = snackbarHostState,
                                navController = navController
                            )
                        }

                        composable<Screen.SettingsScreen>{
                            SettingScreen(
                                onComposing = {
                                    appBarState.value = it
                                },
                                modifier = Modifier.padding(innerPadding),
                                snackbarHostState = snackbarHostState,

                            )
                        }
                    }
                }
            }
        }
    }

}