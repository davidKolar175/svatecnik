package com.example.svatecnik.repository

import android.util.Log
import com.example.svatecnik.api.RetrofitInstance
import com.example.svatecnik.model.NameObject
import java.lang.Exception

class Repository {

    suspend fun getSvatek(params: Map<String, String>): List<NameObject> {
        return try {
            RetrofitInstance.api.getSvatek(params)

        } catch (e: Exception)  {
            return emptyList()
        }
    }
}