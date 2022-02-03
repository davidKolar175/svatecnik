package com.example.svatecnik

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.svatecnik.repository.Repository

class MainViewModelFactory(private val repository: Repository, private val dataStore: DataStore<Preferences>): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository, dataStore) as T
    }

}