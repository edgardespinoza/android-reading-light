package com.eespinor.lightreading.reading.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eespinor.lightreading.reading.presentation.add.ReadingAddScreen
import com.eespinor.lightreading.reading.presentation.list.ReadingListScreen
import com.eespinor.lightreading.reading.presentation.ui.theme.LightReadingTheme
import dagger.hilt.android.AndroidEntryPoint

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
                                Text("Top app bar")
                            }
                        )
                    },
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Screen.ReadingListScreen.route,
                    ) {
                        composable(
                            route = Screen.ReadingListScreen.route
                        ) {
                            ReadingListScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable(
                            route = Screen.ReadingAddScreen.route + "/{id}"
                        ) {
                            ReadingAddScreen(
                                modifier = Modifier.padding(innerPadding),
                                snackbarHostState = snackbarHostState,
                                navController = navController
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
                                modifier = Modifier.padding(innerPadding),
                                snackbarHostState = snackbarHostState,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }

}