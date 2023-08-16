package com.example.imagemachine.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Machine(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val type: String,
    val qrNumber: Int,
    val lastMaintenance: String
)

class InvalidMachineException(message: String): Exception(message)
