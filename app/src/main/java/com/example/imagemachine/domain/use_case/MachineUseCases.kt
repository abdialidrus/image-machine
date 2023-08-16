package com.example.imagemachine.domain.use_case

import com.example.imagemachine.domain.use_case.form_validation.ValidateMachineName
import com.example.imagemachine.domain.use_case.form_validation.ValidateMachineType
import com.example.imagemachine.domain.use_case.form_validation.ValidateLastMaintenanceDate
import com.example.imagemachine.domain.use_case.form_validation.ValidateQrNumber

data class MachineUseCases(
    val addMachine: AddMachine,
    val addMachineImages: AddMachineImages,
    val deleteMachine: DeleteMachine,
    val deleteMachineImage: DeleteMachineImage,
    val deleteMachineImages: DeleteMachineImages,
    val getMachine: GetMachine,
    val getMachineImages: GetMachineImages,
    val getMachines: GetMachines,
    val getMachineByQrCode: GetMachineByQrCode,
    val validateMachineName: ValidateMachineName,
    val validateMachineType: ValidateMachineType,
    val validateQrNumber: ValidateQrNumber,
    val validateLastMaintenanceDate: ValidateLastMaintenanceDate
)