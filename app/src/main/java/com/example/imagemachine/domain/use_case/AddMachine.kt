package com.example.imagemachine.domain.use_case

import com.example.imagemachine.domain.model.InvalidMachineException
import com.example.imagemachine.domain.model.Machine
import com.example.imagemachine.domain.repository.MachineRepository

class AddMachine(
    private val repository: MachineRepository
) {

    @Throws(InvalidMachineException::class)
    suspend operator fun invoke(machine: Machine): Long {
        if (machine.name.isBlank()) {
            throw InvalidMachineException("The name of the Machine can't be empty")
        }
        if (machine.type.isBlank()) {
            throw InvalidMachineException("The type of the Machine can't be empty")
        }

        return repository.upsertMachine(machine)
    }
}