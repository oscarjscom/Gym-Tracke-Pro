package com.example.gymtrackerpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gymtrackerpro.ui.screens.AgregarRutinaScreen
import com.example.gymtrackerpro.ui.screens.DetalleRutinaScreen
import com.example.gymtrackerpro.ui.screens.ListaRutinasScreen
import com.example.gymtrackerpro.ui.screens.LoginScreen
import com.example.gymtrackerpro.ui.screens.MenuPrincipalScreen
import com.example.gymtrackerpro.ui.screens.PerfilUsuarioScreen
import com.example.gymtrackerpro.ui.screens.RegistroScreen
import com.example.gymtrackerpro.ui.theme.GymTrackerProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GymTrackerProTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(navController)
                    }
                    composable("registro") {
                        RegistroScreen(navController)
                    }
                    composable(
                        "menu/{usuarioId}",
                        arguments = listOf(navArgument("usuarioId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0
                        MenuPrincipalScreen(navController, usuarioId)
                    }
                    composable(
                        "agregar_rutina/{usuarioId}",
                        arguments = listOf(navArgument("usuarioId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0
                        AgregarRutinaScreen(navController, usuarioId)
                    }
                    composable(
                        "lista_rutinas/{usuarioId}",
                        arguments = listOf(navArgument("usuarioId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0
                        ListaRutinasScreen(navController, usuarioId)
                    }
                    composable(
                        "detalle_rutina/{rutinaId}/{usuarioId}",
                        arguments = listOf(
                            navArgument("rutinaId") { type = NavType.IntType },
                            navArgument("usuarioId") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val rutinaId = backStackEntry.arguments?.getInt("rutinaId") ?: 0
                        val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0
                        DetalleRutinaScreen(navController, rutinaId, usuarioId)
                    }
                    composable(
                        "perfil/{usuarioId}",
                        arguments = listOf(navArgument("usuarioId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0
                        PerfilUsuarioScreen(navController, usuarioId)
                    }
                }
            }
        }
    }
}
