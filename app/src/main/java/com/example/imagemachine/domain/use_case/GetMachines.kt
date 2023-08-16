package com.example.imagemachine.domain.use_case

import com.example.imagemachine.domain.model.Machine
import com.example.imagemachine.domain.repository.MachineRepository
import com.example.imagemachine.domain.util.MachineOrder
import com.example.imagemachine.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMachines(
    private val repository: MachineRepository
) {

    operator fun invoke(
        machineOrder: MachineOrder = MachineOrder.Name(OrderType.Ascending)
    ): Flow<List<Machine>> {
        return repository.getMachines().map { machines ->
            when(machineOrder.orderType) {
                is OrderType.Ascending -> {
                    when(machineOrder) {
                        is MachineOrder.Name -> machines.sortedBy { it.name.lowercase() }
                        is MachineOrder.Type -> machines.sortedBy { it.type.lowercase() }
                    }
                }
                is OrderType.Descending -> {
                    when(machineOrder) {
                        is MachineOrder.Name -> machines.sortedByDescending { it.name.lowercase() }
                        is MachineOrder.Type -> machines.sortedByDescending { it.type.lowercase() }
                    }
                }
            }
        }
    }
}