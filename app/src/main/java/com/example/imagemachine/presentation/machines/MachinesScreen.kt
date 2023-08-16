package com.example.imagemachine.presentation.machines

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.imagemachine.R
import com.example.imagemachine.presentation.machines.components.EmptyListMessageSection
import com.example.imagemachine.presentation.machines.components.MachineItem
import com.example.imagemachine.presentation.machines.components.OrderSection
import com.example.imagemachine.presentation.ui.theme.Typography
import com.example.imagemachine.presentation.util.Screen

@ExperimentalAnimationApi
@Composable
fun MachinesScreen(
    navController: NavController,
    viewModel: MachinesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        floatingActionButton = {
            if (state.machines.isNotEmpty()) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.AddEditMachineScreen.route)
                    },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add machine"
                    )
                }
            }
        },
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Image Machine",
                    style = Typography.h5,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (state.machines.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(MachinesEvent.ToggleOrderSection)
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.round_sort_24),
                                contentDescription = "sort-machine-data"
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            navController.navigate(
                                Screen.AddEditMachineScreen.route + "?shouldOpenQrScanner=true"
                            )
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_qr_code_scanner_24),
                            contentDescription = "open-qr-scanner"
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    machineOrder = state.machineOrder,
                    onOrderChange = {
                        viewModel.onEvent(MachinesEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            state.machines.ifEmpty {
                EmptyListMessageSection {
                    navController.navigate(Screen.AddEditMachineScreen.route)
                }
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.machines) { machine ->
                    MachineItem(
                        machine = machine,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditMachineScreen.route + "?machineId=${machine.id}"
                                )
                            }
                    )
                    Divider(
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}