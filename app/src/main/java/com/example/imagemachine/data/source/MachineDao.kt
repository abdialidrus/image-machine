package com.example.imagemachine.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imagemachine.domain.model.Machine
import com.example.imagemachine.domain.model.MachineImage
import kotlinx.coroutines.flow.Flow

@Dao
interface MachineDao {

    @Query("SELECT * FROM machine")
    fun getMachines(): Flow<List<Machine>>

    @Query("SELECT * FROM machine WHERE id = :id")
    suspend fun getMachineById(id: Int): Machine?

    @Query("SELECT * FROM machine WHERE qrNumber = :number")
    suspend fun getMachineByQrNumber(number: Int): Machine?

    @Query("SELECT * FROM machineimage WHERE machineId = :machineId")
    fun getMachineImagesByMachineId(machineId: Int): Flow<List<MachineImage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMachine(machine: Machine): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMachineImages(machineImages: List<MachineImage>)

    @Delete
    suspend fun deleteMachine(machine: Machine)

    @Delete
    suspend fun deleteMachineImage(machineImage: MachineImage)

    @Delete
    suspend fun deleteMachineImages(machineImages: List<MachineImage>)
}