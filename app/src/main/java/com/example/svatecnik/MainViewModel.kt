package com.example.svatecnik

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svatecnik.model.NameObject
import com.example.svatecnik.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    val myResponse: MutableLiveData<NameObject> = MutableLiveData()

    init {
        viewModelScope.launch {
            getCurrentSvatek()
            delay(2000)
            _isLoading.value = false;
        }
    }

    fun getCurrentSvatek() {
        viewModelScope.launch {
            val sdf = SimpleDateFormat("ddMM")
            val currentDate = sdf.format(Date())
            val response = repository.getSvatek(buildMap { put("date", currentDate) })
            myResponse.value = response.first();
        }
    }

    fun getSvatekByName(name: String) {
        viewModelScope.launch {
            val response = repository.getSvatek(buildMap { put("name", name) })
            myResponse.value = response.first();
        }
    }

    fun getSvatekByDate(date: String) {
        viewModelScope.launch {
            val response = repository.getSvatek(buildMap { put("date", date) })
            myResponse.value = response.first();
        }
    }
}