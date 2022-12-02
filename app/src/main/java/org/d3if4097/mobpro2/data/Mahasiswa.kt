package org.d3if4097.mobpro2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mahasiswa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nim: String,
    val nama: String,
    val kelas: String
)
