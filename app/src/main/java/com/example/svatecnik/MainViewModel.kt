package com.example.svatecnik

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.svatecnik.model.NameObject
import com.example.svatecnik.repository.Repository
import com.example.svatecnik.utils.Constants.Companion.FAVOURITE_NAMES_KEY
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class MainViewModel(
    private val repository: Repository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    val myResponse: MutableLiveData<NameObject> = MutableLiveData()
    val fakeNames: MutableLiveData<MutableList<String>> = MutableLiveData()


    lateinit var lastPlayedSong: Flow<Set<String>>

    init {
        viewModelScope.launch {
            getCurrentSvatek()
            delay(2000)
            _isLoading.value = false
            fakeNames.value = mutableListOf("David")

            lastPlayedSong = dataStore.data.map { preferences ->
                preferences[FAVOURITE_NAMES_KEY] ?: buildSet { "asd" }
            }
        }
    }

    fun setFakeName() {
        viewModelScope.launch {

            //fakeNames.value = mutableListOf("Igor")
            dataStore.edit { settings ->
                settings[FAVOURITE_NAMES_KEY] = buildSet { "Igor" }
            }

        }
    }

    fun getCurrentSvatek() {
        viewModelScope.launch {
            val sdf = SimpleDateFormat("ddMM")
            val currentDate = sdf.format(Date())
            val response = repository.getSvatek(buildMap { put("date", currentDate) })

            if (response.isNotEmpty())
                myResponse.value = response.first();
            else
                myResponse.value = null
        }
    }

    fun getSvatekByName(name: String) {
        viewModelScope.launch {
            val response = repository.getSvatek(buildMap { put("name", name) })

            if (response.isNotEmpty())
                myResponse.value = response.first();
            else
                myResponse.value = null
        }
    }

    fun getSvatekByDate(date: String) {
        viewModelScope.launch {
            val response = repository.getSvatek(buildMap { put("date", date) })

            if (response.isNotEmpty())
                myResponse.value = response.first();
            else
                myResponse.value = null
        }
    }
}