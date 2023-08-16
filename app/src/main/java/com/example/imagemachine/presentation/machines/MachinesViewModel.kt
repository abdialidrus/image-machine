package com.example.imagemachine.presentation.machines

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagemachine.domain.use_case.MachineUseCases
import com.example.imagemachine.domain.util.MachineOrder
import com.example.imagemachine.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MachinesViewModel @Inject constructor(
    private val machineUseCases: MachineUseCases
): ViewModel() {

    private val _state = mutableStateOf(MachinesState())
    val state: State<MachinesState> = _state

    private var getMachinesJob: Job? = null

    init {
        getMachines(MachineOrder.Name(OrderType.Ascending))
    }

    fun onEvent(event: MachinesEvent) {
        when (event) {
            is MachinesEvent.Order -> {
                if (state.value.machineOrder::class == event.machineOrder::class &&
                    state.value.machineOrder.orderType == event.machineOrder.orderType
                ) {
                    return
                }
                getMachines(event.machineOrder)
            }
            is MachinesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getMachines(machineOrder: MachineOrder) {
        getMachinesJob?.cancel()
        getMachinesJob = machineUseCases.getMachines(machineOrder)
            .onEach { machines ->
                _state.value = state.value.copy(
                    machines = machines,
                    machineOrder = machineOrder
                )
            }
            .launchIn(viewModelScope)
    }

}