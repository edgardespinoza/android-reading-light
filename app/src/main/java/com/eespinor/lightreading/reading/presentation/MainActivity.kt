package com.eespinor.lightreading.reading.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.eespinor.lightreading.common.BottomNavigation
import com.eespinor.lightreading.common.ScaffoldViewState
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

                var fabVisible by remember { mutableStateOf(true) }


                var scaffoldViewState by remember {
                    mutableStateOf(ScaffoldViewState())
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
                                scaffoldViewState.topAppBarTitle?.let {
                                    Text(text = it)
                                }
                            }
                        )
                    },
                    bottomBar = {
                        if(scaffoldViewState.isBottomBarVisible) {
                            BottomNavigation(navController = navController, scaffoldViewState= scaffoldViewState)
                        }
                    },
                    floatingActionButton = {

                        scaffoldViewState.fabIcon?.let { fabIcon ->
                            AnimatedVisibility(visible = fabVisible) {

                                FloatingActionButton(onClick = { scaffoldViewState.onFabClick?.invoke() }) {
                                    Icon(fabIcon,
                                        contentDescription = scaffoldViewState.fabText?.let {
                                            stringResource(
                                                id = it
                                            )
                                        }
                                    )
                                }
                            }
                        }

                    }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.ReadingListScreen,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Screen.ReadingListScreen>{

                            ReadingListScreen(
                                navController = navController,
                                onScaffoldViewState = {
                                    scaffoldViewState = it
                                },
                                onFabVisible = {
                                    fabVisible = it
                                }
                            )
                        }
                        composable<Screen.ReadingEditScreen>(
                            typeMap = mapOf(typeOf<Screen.ReadingEditScreen.RoomEditData>() to RoomEditDataNavType)
                        ) { backStackEntry ->
                            val item = backStackEntry.toRoute<Screen.ReadingEditScreen>()

                            ReadingAddScreen(
                                snackbarHostState = snackbarHostState,
                                navController = navController,
                                item = item,
                                onScaffoldViewState = {
                                    scaffoldViewState = it
                                }
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
                                snackbarHostState = snackbarHostState,
                                navController = navController,
                                onScaffoldViewState = {
                                    scaffoldViewState = it
                                }
                            )
                        }

                        composable<Screen.SettingsScreen> {
                            SettingScreen(
                                snackbarHostState = snackbarHostState,
                                onScaffoldViewState = {
                                    scaffoldViewState = it
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}