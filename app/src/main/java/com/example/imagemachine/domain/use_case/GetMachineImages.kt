package com.example.imagemachine.domain.use_case

import com.example.imagemachine.domain.model.MachineImage
import com.example.imagemachine.domain.repository.MachineRepository
import kotlinx.coroutines.flow.Flow

class GetMachineImages(
    private val repository: MachineRepository
) {

    operator fun invoke(machineId: Int): Flow<List<MachineImage>> {
        return repository.getMachineImagesByMachineId(machineId)
    }
}