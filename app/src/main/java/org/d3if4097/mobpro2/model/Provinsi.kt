package org.d3if4097.mobpro2.model

import com.squareup.moshi.Json

data class Provinsi(
    val key: String,
    @Json(name = "jumlah_dirawat") val dirawat: Int,
    val lokasi: Lokasi
)
