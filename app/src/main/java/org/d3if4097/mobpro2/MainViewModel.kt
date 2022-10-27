package org.d3if4097.mobpro2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if4097.mobpro2.data.Mahasiswa
import org.d3if4097.mobpro2.data.MahasiswaDao

class MainViewModel(private val db : MahasiswaDao) : ViewModel() {
    fun insertData(mahasiswa: Mahasiswa) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insertData(mahasiswa)
            }
        }
    }
}