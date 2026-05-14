package com.example.gymtrackerpro.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gymtrackerpro.data.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE nombre_usuario = :user AND password = :pass LIMIT 1")
    suspend fun buscarPorCredenciales(user: String, pass: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE id = :id")
    suspend fun buscarPorId(id: Int): Usuario?

    @Query("SELECT * FROM Usuario WHERE nombre_usuario = :nombreUsuario LIMIT 1")
    suspend fun buscarPorNombreUsuario(nombreUsuario: String): Usuario?
}
