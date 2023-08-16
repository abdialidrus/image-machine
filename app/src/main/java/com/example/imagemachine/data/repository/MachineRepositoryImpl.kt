package com.example.imagemachine.data.repository

import com.example.imagemachine.data.source.MachineDao
import com.example.imagemachine.domain.model.Machine
import com.example.imagemachine.domain.model.MachineImage
import com.example.imagemachine.domain.repository.MachineRepository
import kotlinx.coroutines.flow.Flow

class MachineRepositoryImpl(
    private val dao: MachineDao
): MachineRepository {
    override fun getMachines(): Flow<List<Machine>> {
        return dao.getMachines()
    }

    override fun getMachineImagesByMachineId(machineId: Int): Flow<List<MachineImage>> {
        return dao.getMachineImagesByMachineId(machineId)
    }

    override suspend fun getMachineById(id: Int): Machine? {
        return dao.getMachineById(id)
    }

    override suspend fun getMachineByQrNumber(code: String): Machine? {
        val qrNumber = code.toIntOrNull() ?: return null
        return dao.getMachineByQrNumber(qrNumber)
    }

    override suspend fun upsertMachine(machine: Machine): Long {
        return dao.upsertMachine(machine)
    }

    override suspend fun upsertMachineImages(machineImages: List<MachineImage>) {
        dao.upsertMachineImages(machineImages)
    }

    override suspend fun deleteMachine(machine: Machine) {
        dao.deleteMachine(machine)
    }

    override suspend fun deleteMachineImage(machineImage: MachineImage) {
        dao.deleteMachineImage(machineImage)
    }

    override suspend fun deleteMachineImages(machineImages: List<MachineImage>) {
        dao.deleteMachineImages(machineImages)
    }
}