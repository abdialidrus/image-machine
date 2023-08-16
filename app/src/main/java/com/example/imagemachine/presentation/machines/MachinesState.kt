package com.example.imagemachine.presentation.machines

import com.example.imagemachine.domain.model.Machine
import com.example.imagemachine.domain.util.MachineOrder
import com.example.imagemachine.domain.util.OrderType

data class MachinesState(
    val machines: List<Machine> = emptyList(),
    val machineOrder: MachineOrder = MachineOrder.Name(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false
)