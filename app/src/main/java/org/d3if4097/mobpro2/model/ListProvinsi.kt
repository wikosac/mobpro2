package org.d3if4097.mobpro2.model

import com.squareup.moshi.Json
data class ListProvinsi(
    @Json(name = "list_data") val data: List<Provinsi>
)
