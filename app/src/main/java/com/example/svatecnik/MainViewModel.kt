package com.example.svatecnik

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svatecnik.model.NameObject
import com.example.svatecnik.repository.Repository
import com.example.svatecnik.utils.Constants.Companion.FAVOURITE_NAMES_KEY
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val repository: Repository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    val svatekResponse: MutableLiveData<NameObject> = MutableLiveData()
    val favouriteNames: MutableLiveData<Set<String>> = MutableLiveData()
    val isHomeFragmentShow: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewModelScope.launch {
            isHomeFragmentShow.value = true
            getCurrentSvatek()
            delay(1500)
            _isLoading.value = false

            dataStore.data.map {
                it[FAVOURITE_NAMES_KEY] ?: emptySet()
            }.collect {
                favouriteNames.value = it
            }
        }
    }

    fun deleteFavouriteNames() {
        viewModelScope.launch {
            dataStore.edit {
                it[FAVOURITE_NAMES_KEY] = emptySet()
            }
        }
    }

    fun addNewFavouriteName(newName: String) {
        viewModelScope.launch {
            val currentFavouriteNames = favouriteNames.value
            val newMutableSet = mutableSetOf<String>()

            if (currentFavouriteNames?.contains(newName) == true)
                newMutableSet.addAll(currentFavouriteNames.filter { x -> x != newName })
            else if (currentFavouriteNames != null) {
                newMutableSet.addAll(currentFavouriteNames.toSet())
                newMutableSet.add(newName)
            }

            dataStore.edit { settings ->
                val immutableSet: Set<String> = newMutableSet.toSet()
                settings[FAVOURITE_NAMES_KEY] = immutableSet
                favouriteNames.value = immutableSet
            }
        }
    }

    fun getCurrentSvatek() {
        viewModelScope.launch {
            val sdf = SimpleDateFormat("ddMM")
            val currentDate = sdf.format(Date())
            val response = repository.getSvatek(buildMap { put("date", currentDate) })

            if (response.isNotEmpty())
                svatekResponse.value = response.first();
            else
                svatekResponse.value = null
        }
    }

    fun getSvatekByName(name: String) {
        viewModelScope.launch {
            val response = repository.getSvatek(buildMap { put("name", name) })

            if (response.isNotEmpty())
                svatekResponse.value = response.first();
            else
                svatekResponse.value = null
        }
    }

    fun getSvatekByDate(date: String) {
        viewModelScope.launch {
            val response = repository.getSvatek(buildMap { put("date", date) })

            if (response.isNotEmpty())
                svatekResponse.value = response.first();
            else
                svatekResponse.value = null
        }
    }
}