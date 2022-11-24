package org.d3if4097.mobpro2.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase

class MahasiswaDb private constructor() {

    private val database = FirebaseDatabase.getInstance().getReference(PATH)

    val dao = object : MahasiswaDao {
        override fun insertData(mahasiswa: Mahasiswa) {
            database.push().setValue(mahasiswa)
        }
        override fun getData(): LiveData<List<Mahasiswa>> {
            val data = MutableLiveData<List<Mahasiswa>>()
            data.value = ArrayList()
            return data
        }
        override fun deleteData(ids: List<String>) {
        }
    }
    companion object {
        private const val PATH = "mahasiswa"
        @Volatile
        private var INSTANCE: MahasiswaDb? = null
        fun getInstance(): MahasiswaDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = MahasiswaDb()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}