package com.example.imagemachine.domain.repository

import com.example.imagemachine.domain.model.Machine
import com.example.imagemachine.domain.model.MachineImage
import kotlinx.coroutines.flow.Flow

interface MachineRepository {
    fun getMachines(): Flow<List<Machine>>
    fun getMachineImagesByMachineId(machineId: Int): Flow<List<MachineImage>>
    suspend fun getMachineById(id: Int): Machine?
    suspend fun getMachineByQrNumber(code: String): Machine?
    suspend fun upsertMachine(machine: Machine): Long
    suspend fun upsertMachineImages(machineImages: List<MachineImage>)
    suspend fun deleteMachine(machine: Machine)
    suspend fun deleteMachineImage(machineImage: MachineImage)
    suspend fun deleteMachineImages(machineImages: List<MachineImage>)
}