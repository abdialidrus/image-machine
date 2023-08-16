package com.example.imagemachine.domain.use_case

import com.example.imagemachine.domain.model.Machine
import com.example.imagemachine.domain.repository.MachineRepository

class DeleteMachine(
    private val repository: MachineRepository
) {

    suspend operator fun invoke(machine: Machine) {
        repository.deleteMachine(machine)
    }
}