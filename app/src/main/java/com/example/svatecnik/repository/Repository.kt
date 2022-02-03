package com.example.svatecnik.repository

import com.example.svatecnik.api.RetrofitInstance
import com.example.svatecnik.model.NameObject

class Repository {

    suspend fun getSvatek(params: Map<String, String>): List<NameObject> {
        return try {
            RetrofitInstance.api.getSvatek(params)
        } catch (e: Exception)  {
            return emptyList()
        }
    }
}