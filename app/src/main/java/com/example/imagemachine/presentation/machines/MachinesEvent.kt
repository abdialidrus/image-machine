package com.example.imagemachine.presentation.machines

import com.example.imagemachine.domain.util.MachineOrder

sealed class MachinesEvent {
    data class Order(val machineOrder: MachineOrder): MachinesEvent()
    object ToggleOrderSection: MachinesEvent()
}