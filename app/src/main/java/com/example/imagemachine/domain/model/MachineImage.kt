package com.example.imagemachine.domain.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MachineImage(
    @PrimaryKey val id: Int? = null,
    val machineId: Long,
    val uri: String
)
