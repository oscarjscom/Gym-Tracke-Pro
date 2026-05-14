package com.example.gymtrackerpro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymtrackerpro.data.database.AppDatabase
import com.example.gymtrackerpro.data.model.Rutina
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaRutinasScreen(navController: NavController, usuarioId: Int) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val rutinaDao = db.rutinaDao()
    val scope = rememberCoroutineScope()
    var rutinas by remember { mutableStateOf<List<Rutina>>(emptyList()) }
    var rutinaAEliminar by remember { mutableStateOf<Rutina?>(null) }

    LaunchedEffect(Unit) {
        rutinas = rutinaDao.listarPorUsuario(usuarioId)
    }

    if (rutinaAEliminar != null) {
        AlertDialog(
            onDismissRequest = { rutinaAEliminar = null },
            title = { Text("Eliminar rutina") },
            text = { Text("¿Estás seguro de que deseas eliminar esta rutina?") },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        rutinaDao.eliminar(rutinaAEliminar!!)
                        rutinas = rutinaDao.listarPorUsuario(usuarioId)
                        rutinaAEliminar = null
                    }
                }) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { rutinaAEliminar = null }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis rutinas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("agregar_rutina/$usuarioId") }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(rutinas) { rutina ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.foundation.layout.Column(modifier = Modifier.weight(1f)) {
                            Text(rutina.ejercicio, fontWeight = FontWeight.Bold)
                            Text(
                                rutina.grupoMuscular,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text("${rutina.series} series × ${rutina.repeticiones} reps · ${rutina.pesoKg} kg · ${rutina.fecha}")
                        }
                        IconButton(onClick = {
                            navController.navigate("detalle_rutina/${rutina.id}/$usuarioId")
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = null)
                        }
                        IconButton(onClick = { rutinaAEliminar = rutina }) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}
