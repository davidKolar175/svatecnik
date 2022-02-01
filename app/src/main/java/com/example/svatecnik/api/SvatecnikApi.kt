package com.example.svatecnik.api

import com.example.svatecnik.model.NameObject
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SvatecnikApi {
    @GET("json")
    suspend fun getSvatek(@QueryMap params: Map<String, String>): List<NameObject>
}