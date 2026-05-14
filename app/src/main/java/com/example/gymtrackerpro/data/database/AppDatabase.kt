package com.example.gymtrackerpro.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gymtrackerpro.data.dao.RutinaDao
import com.example.gymtrackerpro.data.dao.UsuarioDao
import com.example.gymtrackerpro.data.model.Rutina
import com.example.gymtrackerpro.data.model.Usuario

@Database(entities = [Usuario::class, Rutina::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun rutinaDao(): RutinaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gymtracker_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
