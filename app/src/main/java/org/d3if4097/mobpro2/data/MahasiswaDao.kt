package org.d3if4097.mobpro2.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface MahasiswaDao {
    @Insert
    fun insertData(mahasiswa: Mahasiswa)
}