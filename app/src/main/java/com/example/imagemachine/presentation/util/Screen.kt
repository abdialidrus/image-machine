package com.example.imagemachine.presentation.util

sealed class Screen(val route: String) {
    object MachinesScreen: Screen("machines_screen")
    object AddEditMachineScreen: Screen("add_edit_machine_screen")
}