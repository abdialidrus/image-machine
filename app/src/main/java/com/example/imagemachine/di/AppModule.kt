package com.example.imagemachine.di

import android.app.Application
import androidx.room.Room
import com.example.imagemachine.data.repository.MachineRepositoryImpl
import com.example.imagemachine.data.source.MachineDatabase
import com.example.imagemachine.domain.repository.MachineRepository
import com.example.imagemachine.domain.use_case.AddMachine
import com.example.imagemachine.domain.use_case.AddMachineImages
import com.example.imagemachine.domain.use_case.DeleteMachine
import com.example.imagemachine.domain.use_case.DeleteMachineImage
import com.example.imagemachine.domain.use_case.DeleteMachineImages
import com.example.imagemachine.domain.use_case.GetMachine
import com.example.imagemachine.domain.use_case.GetMachineByQrCode
import com.example.imagemachine.domain.use_case.GetMachineImages
import com.example.imagemachine.domain.use_case.GetMachines
import com.example.imagemachine.domain.use_case.MachineUseCases
import com.example.imagemachine.domain.use_case.form_validation.ValidateLastMaintenanceDate
import com.example.imagemachine.domain.use_case.form_validation.ValidateMachineName
import com.example.imagemachine.domain.use_case.form_validation.ValidateMachineType
import com.example.imagemachine.domain.use_case.form_validation.ValidateQrNumber
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMachineDatabase(app: Application): MachineDatabase {
        return Room.databaseBuilder(
            app,
            MachineDatabase::class.java,
            MachineDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMachineRepository(db: MachineDatabase): MachineRepository {
        return MachineRepositoryImpl(db.machineDao)
    }

    @Provides
    @Singleton
    fun provideMachineUseCases(repository: MachineRepository): MachineUseCases {
        return MachineUseCases(
            addMachine = AddMachine(repository),
            addMachineImages = AddMachineImages(repository),
            deleteMachine = DeleteMachine(repository),
            deleteMachineImage = DeleteMachineImage(repository),
            deleteMachineImages = DeleteMachineImages(repository),
            getMachine = GetMachine(repository),
            getMachineImages = GetMachineImages(repository),
            getMachines = GetMachines(repository),
            getMachineByQrCode = GetMachineByQrCode(repository),
            validateMachineName = ValidateMachineName(),
            validateMachineType = ValidateMachineType(),
            validateQrNumber = ValidateQrNumber(),
            validateLastMaintenanceDate = ValidateLastMaintenanceDate()
        )
    }
}