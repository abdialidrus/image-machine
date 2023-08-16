package com.example.imagemachine.domain.use_case

import com.example.imagemachine.domain.model.Machine
import com.example.imagemachine.domain.repository.MachineRepository

class GetMachine(
    private val repository: MachineRepository
) {

    suspend operator fun invoke(id: Int) : Machine? {
        return repository.getMachineById(id)
    }
}