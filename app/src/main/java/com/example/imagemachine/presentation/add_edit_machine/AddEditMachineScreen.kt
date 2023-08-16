package com.example.imagemachine.presentation.add_edit_machine

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.imageLoader
import coil.util.DebugLogger
import com.example.imagemachine.R
import com.example.imagemachine.presentation.add_edit_machine.components.QrScannerScreen
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditMachineScreen(
    navController: NavController,
    viewModel: AddEditMachineViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isEditingState = viewModel.isEditing.value
    val formState = viewModel.formState.value
    val scaffoldState = rememberScaffoldState()
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val dateDialogState = rememberMaterialDialogState()

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = 10
        ),
        onResult = { uris ->
            uris.forEach { uri ->
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            viewModel.onEvent(AddEditMachineEvent.SelectedMachineImages(uris))
        }
    )

    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .build()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditMachineViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditMachineViewModel.UiEvent.SaveMachine -> {
                    navController.navigateUp()
                }

                is AddEditMachineViewModel.UiEvent.DeleteMachine -> {
                    navController.navigateUp()
                }
            }
        }
    }

    var imageToShowInFull by remember {
        mutableStateOf<Uri?>(null)
    }

    if (viewModel.isShowQrScanner.value) {
        QrScannerScreen(
            modifier = Modifier.fillMaxSize(),
            context = context,
            onScanResult = { code ->
                viewModel.loadMachineByQrCode(code)
            },
            onPermissionResult = { permissionGranted ->
                if (!permissionGranted) navController.navigateUp()
            }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = if (isEditingState) "Edit Machine Data" else "Add Machine Data")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.navigateUp()
                            }) {
                                Icon(Icons.Rounded.ArrowBack, "back-icon")
                            }
                        },
                        actions = {
                            if (isEditingState) {
                                IconButton(
                                    onClick = {
                                        viewModel.onEvent(AddEditMachineEvent.DeleteMachine)
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Delete,
                                        contentDescription = "delete machine data"
                                    )
                                }
                            }
                        }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            viewModel.onEvent(AddEditMachineEvent.SaveMachine)
                        },
                        backgroundColor = MaterialTheme.colors.primary
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_save_24),
                            contentDescription = "Save machine"
                        )
                    }
                },
                scaffoldState = scaffoldState
            ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    item {
                        Column {
                            OutlinedTextField(
                                value = formState.name,
                                isError = formState.nameError != null,
                                onValueChange = {
                                    viewModel.onEvent(AddEditMachineEvent.NameChanged(it))
                                },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                label = {
                                    Text(text = "Machine name")
                                },
                                placeholder = {
                                    Text(text = "Enter machine name")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                )
                            )
                            if (formState.nameError != null) {
                                Text(
                                    text = formState.nameError,
                                    color = MaterialTheme.colors.error,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = formState.type,
                                isError = formState.typeError != null,
                                onValueChange = {
                                    viewModel.onEvent(AddEditMachineEvent.TypeChanged(it))
                                },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                label = {
                                    Text(text = "Machine type")
                                },
                                placeholder = {
                                    Text(text = "Enter machine type")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                )
                            )
                            if (formState.typeError != null) {
                                Text(
                                    text = formState.typeError,
                                    color = MaterialTheme.colors.error,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = formState.qrNumber,
                                isError = formState.qrNumberError != null,
                                onValueChange = {
                                    viewModel.onEvent(AddEditMachineEvent.QrNumberChanged(it))
                                },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                label = {
                                    Text(text = "Machine QR number")
                                },
                                placeholder = {
                                    Text(text = "Enter machine QR number")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                )
                            )
                            if (formState.qrNumberError != null) {
                                Text(
                                    text = formState.qrNumberError,
                                    color = MaterialTheme.colors.error,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = formState.lastMaintenanceDate,
                                isError = formState.lastMaintenanceDateError != null,
                                onValueChange = {
                                    viewModel.onEvent(AddEditMachineEvent.LastMaintenanceDateChanged(it))
                                },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                label = {
                                    Text(text = "Machine last maintenance date")
                                },
                                placeholder = {
                                    Text(text = "Enter machine last maintenance date")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text
                                ),
                                trailingIcon = {
                                    IconButton(onClick = { dateDialogState.show() }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.round_calendar_month_24),
                                            contentDescription = "trailing-icon-calendar"
                                        )
                                    }
                                },
                                readOnly = true
                            )
                            if (formState.lastMaintenanceDateError != null) {
                                Text(
                                    text = formState.lastMaintenanceDateError,
                                    color = MaterialTheme.colors.error,
                                    fontSize = 12.sp,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                )
                            }
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Button(onClick = {
                                multiplePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }) {
                                Text(text = "Select images")
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(viewModel.newlySelectedMachineImages.value.ifEmpty {
                                    viewModel.savedMachineImages.value
                                }) { machineImage ->
                                    Box(
                                        modifier = Modifier
                                            .width(150.dp)
                                            .height(150.dp)
                                            .clickable {
                                                imageToShowInFull = Uri.parse(machineImage.uri)
                                            }
                                    ) {
                                        AsyncImage(
                                            modifier = Modifier
                                                .width(140.dp)
                                                .height(140.dp)
                                                .align(Alignment.BottomStart),
                                            model = Uri.parse(machineImage.uri),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            imageLoader = imageLoader
                                        )
                                        Box(
                                            modifier = Modifier
                                                .width(20.dp)
                                                .height(20.dp)
                                                .background(color = Color.White, shape = CircleShape)
                                                .border(
                                                    width = 1.dp,
                                                    color = Color.Gray,
                                                    shape = CircleShape
                                                )
                                                .align(Alignment.TopEnd),
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    if (machineImage.id != null) {
                                                        viewModel.onEvent(
                                                            AddEditMachineEvent.DeleteSavedMachineImage(
                                                                machineImage
                                                            )
                                                        )
                                                    } else {
                                                        viewModel.onEvent(
                                                            AddEditMachineEvent.DeleteNewlyAddedMachineImage(
                                                                machineImage.uri
                                                            )
                                                        )
                                                    }
                                                })
                                            {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.round_delete_16),
                                                    contentDescription = "delete-machine-image"
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(5.dp))
                                }
                            }
                            MaterialDialog(
                                dialogState = dateDialogState,
                                buttons = {
                                    positiveButton(text = "Ok")
                                    negativeButton(text = "Cancel")
                                }
                            ) {
                                datepicker(
                                    initialDate = LocalDate.now(),
                                    title = "Pick a date"
                                ) {
                                    pickedDate = it
                                    val formattedDate = DateTimeFormatter
                                        .ofPattern("dd-MM-yyyy")
                                        .format(pickedDate)
                                    viewModel.onEvent(
                                        AddEditMachineEvent.LastMaintenanceDateChanged(
                                            formattedDate
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            imageToShowInFull?.let {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black.copy(alpha = 0.8f))
                        .padding(20.dp)
                        .clickable { }
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        model = it,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        imageLoader = imageLoader
                    )

                    IconButton(
                        modifier = Modifier.align(Alignment.TopEnd),
                        onClick = { imageToShowInFull = null })
                    {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "close-full-screen-preview",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}