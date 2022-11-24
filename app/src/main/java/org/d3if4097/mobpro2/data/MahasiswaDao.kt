package org.d3if4097.mobpro2.data

import androidx.lifecycle.LiveData

interface MahasiswaDao {

    fun insertData(mahasiswa: Mahasiswa)

    fun getData(): LiveData<List<Mahasiswa>>

    fun deleteData(ids: List<String>)
}