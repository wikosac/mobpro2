package org.d3if4097.mobpro2

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if4097.mobpro2.model.Data
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

object Covid19Api {
    private const val BASE_URL = "https://data.covid19.go.id/public/api/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()
    val service: ApiService by lazy { retrofit.create(ApiService::class.java) }
    interface ApiService {
        @GET("update.json")
        suspend fun getData(): Data
    }
}