package com.example.gymtrackerpro.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.gymtrackerpro.data.model.Rutina

@Dao
interface RutinaDao {

    @Insert
    suspend fun insertar(rutina: Rutina)

    @Update
    suspend fun actualizar(rutina: Rutina)

    @Delete
    suspend fun eliminar(rutina: Rutina)

    @Query("SELECT * FROM Rutina WHERE usuario_id = :usuarioId")
    suspend fun listarPorUsuario(usuarioId: Int): List<Rutina>

    @Query("SELECT * FROM Rutina WHERE id = :id")
    suspend fun buscarPorId(id: Int): Rutina?

    @Query("SELECT COUNT(*) FROM Rutina WHERE usuario_id = :id")
    suspend fun contarPorUsuario(id: Int): Int

    @Query("SELECT SUM(peso_kg * series * repeticiones) FROM Rutina WHERE usuario_id = :id")
    suspend fun sumarKgPorUsuario(id: Int): Double?
}
