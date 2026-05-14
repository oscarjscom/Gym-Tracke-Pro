package com.example.gymtrackerpro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymtrackerpro.data.database.AppDatabase
import com.example.gymtrackerpro.data.model.Rutina
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarRutinaScreen(navController: NavController, usuarioId: Int) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val rutinaDao = db.rutinaDao()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val gruposMusculares = listOf("Pecho", "Espalda", "Pierna", "Hombro", "Brazo", "Abdomen", "Glúteo")
    var ejercicio by remember { mutableStateOf("") }
    var grupoMuscular by remember { mutableStateOf("") }
    var series by remember { mutableStateOf("") }
    var repeticiones by remember { mutableStateOf("") }
    var pesoKg by remember { mutableStateOf("") }
    var fecha by remember {
        mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
    }
    var expandedDropdown by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val localDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        fecha = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    fun guardar() {
        scope.launch {
            if (ejercicio.isBlank() || grupoMuscular.isBlank()) {
                snackbarHostState.showSnackbar("Completa todos los campos")
                return@launch
            }
            rutinaDao.insertar(
                Rutina(
                    usuarioId = usuarioId,
                    ejercicio = ejercicio,
                    grupoMuscular = grupoMuscular,
                    series = series.toIntOrNull() ?: 0,
                    repeticiones = repeticiones.toIntOrNull() ?: 0,
                    pesoKg = pesoKg.toDoubleOrNull() ?: 0.0,
                    fecha = fecha
                )
            )
            navController.popBackStack()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Nueva rutina") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { guardar() }) {
                        Icon(Icons.Default.Save, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Text("Ejercicio")
            OutlinedTextField(
                value = ejercicio,
                onValueChange = { ejercicio = it },
                placeholder = { Text("Press banca") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Text("Grupo muscular")
            ExposedDropdownMenuBox(
                expanded = expandedDropdown,
                onExpandedChange = { expandedDropdown = it }
            ) {
                OutlinedTextField(
                    value = grupoMuscular,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Seleccionar") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false }
                ) {
                    gruposMusculares.forEach { grupo ->
                        DropdownMenuItem(
                            text = { Text(grupo) },
                            onClick = {
                                grupoMuscular = grupo
                                expandedDropdown = false
                            }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Series")
                    OutlinedTextField(
                        value = series,
                        onValueChange = { series = it },
                        placeholder = { Text("4") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Repeticiones")
                    OutlinedTextField(
                        value = repeticiones,
                        onValueChange = { repeticiones = it },
                        placeholder = { Text("12") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }
            Text("Peso (kg)")
            OutlinedTextField(
                value = pesoKg,
                onValueChange = { pesoKg = it },
                placeholder = { Text("60.5") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Text("Fecha")
            OutlinedTextField(
                value = fecha,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { guardar() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar rutina")
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
