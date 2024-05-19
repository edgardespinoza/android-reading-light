package com.eespinor.lightreading.reading.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eespinor.lightreading.common.Constants
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

                Scaffold(
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
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate(Screen.ReadingAddScreen.route)
                            },
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    },
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.ReadingListScreen.route,
                        ) {
                            composable(
                                route = Screen.ReadingListScreen.route
                            ) {
                                ReadingListScreen(navController)
                            }
                            composable(
                                route = Screen.ReadingAddScreen.route + "/{id}"
                            ) {
                                ReadingAddScreen(navController)
                            }
                            composable(
                             route = Screen.ReadingAddScreen.route
                            ) {
                                ReadingAddScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

