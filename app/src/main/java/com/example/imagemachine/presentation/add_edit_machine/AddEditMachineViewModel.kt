package com.example.imagemachine.presentation.add_edit_machine

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagemachine.domain.model.InvalidMachineException
import com.example.imagemachine.domain.model.Machine
import com.example.imagemachine.domain.model.MachineImage
import com.example.imagemachine.domain.use_case.MachineUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditMachineViewModel @Inject constructor(
    private val machineUseCases: MachineUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isEditing = mutableStateOf(false)
    val isEditing: State<Boolean> = _isEditing

    private val _isShowQrScanner = mutableStateOf(false)
    val isShowQrScanner: State<Boolean> = _isShowQrScanner

    private val _formState = mutableStateOf(AddEditMachineFormState())
    val formState: State<AddEditMachineFormState> = _formState

    private val _newlySelectedMachineImages = mutableStateOf<List<MachineImage>>(emptyList())
    val newlySelectedMachineImages: State<List<MachineImage>> = _newlySelectedMachineImages
    private val _savedMachineImages = mutableStateOf<List<MachineImage>>(emptyList())
    val savedMachineImages: State<List<MachineImage>> = _savedMachineImages

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentMachineId: Int? = null

    private var getMachineImagesJob: Job? = null

    init {
        savedStateHandle.get<Int>("machineId")?.let { machineId ->
            if (machineId != -1) {
                loadMachine(machineId)
            }
        }
        _isShowQrScanner.value = savedStateHandle.get<Boolean>("shouldOpenQrScanner") ?: false
    }

    fun onEvent(event: AddEditMachineEvent) {
        when (event) {
            is AddEditMachineEvent.NameChanged -> {
                _formState.value = _formState.value.copy(name = event.name)
            }

            is AddEditMachineEvent.TypeChanged -> {
                _formState.value = _formState.value.copy(type = event.type)
            }

            is AddEditMachineEvent.QrNumberChanged -> {
                _formState.value = _formState.value.copy(qrNumber = event.qrNumber)
            }

            is AddEditMachineEvent.LastMaintenanceDateChanged -> {
                _formState.value = _formState.value.copy(lastMaintenanceDate = event.date)
            }

            is AddEditMachineEvent.SaveMachine -> {
                saveMachine()
            }

            is AddEditMachineEvent.DeleteMachine -> {
                deleteMachine()
            }

            is AddEditMachineEvent.SelectedMachineImages -> {
                _newlySelectedMachineImages.value = event.uris.map { uri ->
                    MachineImage(
                        machineId = 0,
                        uri = uri.toString()
                    )
                }
            }

            is AddEditMachineEvent.LoadSavedMachineImages -> {
                _savedMachineImages.value = event.machineImages
            }

            is AddEditMachineEvent.DeleteNewlyAddedMachineImage -> {
                val filteredUris = newlySelectedMachineImages.value.filter { it.uri != event.uri }
                _newlySelectedMachineImages.value = filteredUris
            }

            is AddEditMachineEvent.DeleteSavedMachineImage -> {
                if (event.machineImage.id != null && event.machineImage.machineId > 0) {
                    viewModelScope.launch {
                        machineUseCases.deleteMachineImage(event.machineImage)
                        currentMachineId?.let {
                            getMachineImages(it)
                        }
                    }
                }
            }
        }
    }

    private fun saveMachine() {
        val nameResult = machineUseCases.validateMachineName.execute(formState.value.name)
        val typeResult = machineUseCases.validateMachineType.execute(formState.value.type)
        val qrNumberResult = machineUseCases.validateQrNumber.execute(formState.value.qrNumber)
        val lastDateResult =
            machineUseCases.validateLastMaintenanceDate.execute(formState.value.lastMaintenanceDate)

        val hasError = listOf(
            nameResult,
            typeResult,
            qrNumberResult,
            lastDateResult
        ).any { !it.successful }

        if (hasError) {
            _formState.value = _formState.value.copy(
                nameError = nameResult.errorMessage,
                typeError = typeResult.errorMessage,
                qrNumberError = qrNumberResult.errorMessage,
                lastMaintenanceDateError = lastDateResult.errorMessage,
            )
            return
        }

        viewModelScope.launch {
            try {
                val insertedMachineId = machineUseCases.addMachine(
                    Machine(
                        id = currentMachineId,
                        name = _formState.value.name,
                        type = _formState.value.type,
                        qrNumber = _formState.value.qrNumber.toInt(),
                        lastMaintenance = _formState.value.lastMaintenanceDate
                    )
                )
                machineUseCases.deleteMachineImages(_savedMachineImages.value)
                if (newlySelectedMachineImages.value.isNotEmpty()) {
                    machineUseCases.addMachineImages(newlySelectedMachineImages.value.map {
                        MachineImage(
                            machineId = insertedMachineId,
                            uri = it.uri
                        )
                    })
                } else {
                    machineUseCases.addMachineImages(savedMachineImages.value.map {
                        MachineImage(
                            machineId = insertedMachineId,
                            uri = it.uri
                        )
                    })
                }
                _eventFlow.emit(UiEvent.SaveMachine)
            } catch (e: InvalidMachineException) {
                _eventFlow.emit(
                    UiEvent.ShowSnackBar(
                        message = e.message ?: "Couldn't save machine"
                    )
                )
            }
        }
    }

    private fun deleteMachine() {
        viewModelScope.launch {
            try {
                machineUseCases.deleteMachine(
                    Machine(
                        id = currentMachineId,
                        name = _formState.value.name,
                        type = _formState.value.type,
                        qrNumber = _formState.value.qrNumber.toInt(),
                        lastMaintenance = _formState.value.lastMaintenanceDate
                    )
                )

                machineUseCases.deleteMachineImages(_savedMachineImages.value)

                _eventFlow.emit(UiEvent.DeleteMachine)
            } catch (e: InvalidMachineException) {
                _eventFlow.emit(
                    UiEvent.ShowSnackBar(
                        message = e.message ?: "Couldn't delete machine"
                    )
                )
            }
        }
    }

    private fun getMachineImages(machineId: Int) {
        getMachineImagesJob?.cancel()
        getMachineImagesJob = machineUseCases.getMachineImages(machineId)
            .onEach { machineImages ->
                onEvent(
                    AddEditMachineEvent.LoadSavedMachineImages(machineImages)
                )
            }
            .launchIn(viewModelScope)
    }

    private fun hideQrScanner() {
        _isShowQrScanner.value = false
    }

    private fun loadMachine(machineId: Int) {
        viewModelScope.launch {
            machineUseCases.getMachine(machineId)?.also { machine ->
                currentMachineId = machine.id
                _isEditing.value = true

                _formState.value = _formState.value.copy(
                    name = machine.name,
                    type = machine.type,
                    qrNumber = machine.qrNumber.toString(),
                    lastMaintenanceDate = machine.lastMaintenance
                )

                getMachineImages(machineId)
            }
        }
    }

    fun loadMachineByQrCode(code: String) {
        if (code.isBlank()) return
        if (code.toIntOrNull() == null) return

        viewModelScope.launch {
            val machine = machineUseCases.getMachineByQrCode(code)
            machine?.id?.let { machineId ->
                loadMachine(machineId)
                hideQrScanner()
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveMachine : UiEvent()
        object DeleteMachine : UiEvent()
    }
}