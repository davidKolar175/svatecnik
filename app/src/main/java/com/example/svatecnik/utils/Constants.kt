package com.example.svatecnik.utils

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

class Constants {
    companion object {
        const val BASE_URL = "https://svatky.adresa.info"
        const val FAVOURITE_NAME_KEY = "FavouriteNames"

        val FAVOURITE_NAMES_KEY = stringSetPreferencesKey("FavouriteNamesKey")
    }


}