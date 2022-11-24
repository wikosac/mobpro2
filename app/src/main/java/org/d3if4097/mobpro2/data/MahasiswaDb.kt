package org.d3if4097.mobpro2.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MahasiswaDb private constructor() {
    val dao = object : MahasiswaDao {
        override fun insertData(mahasiswa: Mahasiswa) {
        }
        override fun getData(): LiveData<List<Mahasiswa>> {
            val data = MutableLiveData<List<Mahasiswa>>()
            data.value = ArrayList()
            return data
        }
        override fun deleteData(ids: List<Int>) {
        }
    }
    companion object {
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