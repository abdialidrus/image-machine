package com.example.imagemachine.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.imagemachine.presentation.add_edit_machine.AddEditMachineScreen
import com.example.imagemachine.presentation.machines.MachinesScreen
import com.example.imagemachine.presentation.ui.theme.ImageMachineTheme
import com.example.imagemachine.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageMachineTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MachinesScreen.route
                    ) {
                        composable(route = Screen.MachinesScreen.route) {
                            MachinesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditMachineScreen.route + "?machineId={machineId}&shouldOpenQrScanner={shouldOpenQrScanner}",
                            arguments = listOf(
                                navArgument(name = "machineId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(name = "shouldOpenQrScanner") {
                                    type = NavType.BoolType
                                    defaultValue = false
                                }
                            )
                        ) {
                            AddEditMachineScreen(navController = navController)
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImageMachineTheme {
        Greeting("Android")
    }
}