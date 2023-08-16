package com.example.imagemachine.presentation.add_edit_machine

data class AddEditMachineFormState(
    val name: String = "",
    val nameError: String? = null,
    val type: String = "",
    val typeError: String? = null,
    val qrNumber: String = "",
    val qrNumberError: String? = null,
    val lastMaintenanceDate: String = "",
    val lastMaintenanceDateError: String? = null
)
