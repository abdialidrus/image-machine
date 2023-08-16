package com.example.imagemachine.domain.use_case

import com.example.imagemachine.domain.model.MachineImage
import com.example.imagemachine.domain.repository.MachineRepository

class AddMachineImages(
    private val repository: MachineRepository
) {

    suspend operator fun invoke(machineImages: List<MachineImage>) {
        repository.upsertMachineImages(machineImages)
    }
}