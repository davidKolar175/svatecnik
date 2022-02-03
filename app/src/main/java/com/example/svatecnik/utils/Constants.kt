package com.example.svatecnik.utils

import androidx.datastore.preferences.core.stringSetPreferencesKey

class Constants {
    companion object {
        const val BASE_URL = "https://svatky.adresa.info"
        const val SVATECNIK_DATA_STORE_KEY = "SvatecnikDataStoreKey"

        val FAVOURITE_NAMES_KEY = stringSetPreferencesKey("FavouriteNamesKey")
    }


}