package com.example.imagemachine.presentation.add_edit_machine

import android.net.Uri
import com.example.imagemachine.domain.model.MachineImage

sealed class AddEditMachineEvent {
    data class NameChanged(val name: String): AddEditMachineEvent()
    data class TypeChanged(val type: String): AddEditMachineEvent()
    data class QrNumberChanged(val qrNumber: String): AddEditMachineEvent()
    data class LastMaintenanceDateChanged(val date: String): AddEditMachineEvent()
    data class SelectedMachineImages(val uris: List<Uri>): AddEditMachineEvent()
    data class LoadSavedMachineImages(val machineImages: List<MachineImage>): AddEditMachineEvent()
    data class DeleteNewlyAddedMachineImage(val uri: String): AddEditMachineEvent()
    data class DeleteSavedMachineImage(val machineImage: MachineImage): AddEditMachineEvent()
    object SaveMachine: AddEditMachineEvent()
    object DeleteMachine: AddEditMachineEvent()
}