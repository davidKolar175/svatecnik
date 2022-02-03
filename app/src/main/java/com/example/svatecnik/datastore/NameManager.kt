package com.example.svatecnik.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NameManager(context: Context) {
    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore("MyDatastore")

    private val dataStore : DataStore<Preferences> = context._dataStore

    companion object {
        val FAVOURITE_NAMES_KEY = stringSetPreferencesKey("FavouriteNamesKey")
    }

    suspend fun storeName(name: String){
        dataStore.edit {
            it[FAVOURITE_NAMES_KEY] = buildSet { name }
        }
    }

    val mockNames : Flow<Set<String>> = dataStore.data.map {
        it[FAVOURITE_NAMES_KEY] ?: buildSet { "FakeName" }
    }

    val namesFlow: Flow<Set<String>> = dataStore.data.map {
        it[FAVOURITE_NAMES_KEY] ?: emptySet()
    }

}