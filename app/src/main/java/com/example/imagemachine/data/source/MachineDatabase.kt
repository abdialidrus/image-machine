package com.example.imagemachine.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imagemachine.domain.model.Machine
import com.example.imagemachine.domain.model.MachineImage

@Database(
    version = 1,
    entities = [
        Machine::class,
        MachineImage::class
    ],
    exportSchema = false
)
abstract class MachineDatabase : RoomDatabase() {

    abstract val machineDao: MachineDao

    companion object {
        const val DATABASE_NAME = "machines_db"
    }
}