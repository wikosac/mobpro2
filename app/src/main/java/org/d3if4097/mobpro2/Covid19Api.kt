package org.d3if4097.mobpro2

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

object Covid19Api {
    private const val BASE_URL = "https://data.covid19.go.id/public/api/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    val service: ApiService by lazy { retrofit.create(ApiService::class.java) }
    interface ApiService {
        @GET("update.json")
        suspend fun getData(): String
    }
}