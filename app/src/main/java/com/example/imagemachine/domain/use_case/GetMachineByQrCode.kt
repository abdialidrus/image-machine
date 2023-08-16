package com.example.imagemachine.domain.use_case

import com.example.imagemachine.domain.model.Machine
import com.example.imagemachine.domain.repository.MachineRepository

class GetMachineByQrCode(
    private val repository: MachineRepository
) {

    suspend operator fun invoke(code: String) : Machine? {
        return repository.getMachineByQrNumber(code)
    }
}