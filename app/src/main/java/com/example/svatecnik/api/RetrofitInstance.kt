package com.example.svatecnik.api

import com.example.svatecnik.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SvatecnikApi by lazy {
        retrofit.create(SvatecnikApi::class.java)
    }
}