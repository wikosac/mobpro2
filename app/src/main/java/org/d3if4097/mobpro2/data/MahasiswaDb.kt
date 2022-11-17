package org.d3if4097.mobpro2.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Mahasiswa::class], version = 1, exportSchema = false)
abstract class MahasiswaDb : RoomDatabase() {
    abstract val dao : MahasiswaDao
    companion object {
        @VisibleForTesting
        const val DATABASE_NAME = "mahasiswa.db"

        @Volatile
        private var INSTANCE: MahasiswaDb? = null
        fun getInstance(context: Context): MahasiswaDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MahasiswaDb::class.java,
                        DATABASE_NAME
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}