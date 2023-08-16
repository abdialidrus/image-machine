package com.example.imagemachine.presentation.machines.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.imagemachine.domain.util.MachineOrder
import com.example.imagemachine.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    machineOrder: MachineOrder = MachineOrder.Name(OrderType.Ascending),
    onOrderChange: (MachineOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Name",
                selected = machineOrder is MachineOrder.Name,
                onSelect = { onOrderChange(MachineOrder.Name(machineOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Type",
                selected = machineOrder is MachineOrder.Type,
                onSelect = { onOrderChange(MachineOrder.Type(machineOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = machineOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(machineOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = machineOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(machineOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}