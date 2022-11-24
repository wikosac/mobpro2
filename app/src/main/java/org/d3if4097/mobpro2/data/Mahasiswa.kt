package org.d3if4097.mobpro2.data

import com.google.firebase.database.Exclude

data class Mahasiswa(
    @get:Exclude
    val id: Int = 0,
    val nim: String,
    val nama: String
)
