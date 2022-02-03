package com.example.svatecnik

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.svatecnik.repository.Repository
import com.example.svatecnik.utils.Constants.Companion.SVATECNIK_DATA_STORE_KEY
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val homeFragment = HomeFragment()
    private val favouritesFragment = FavouritesFragment()
    private val dataStore: DataStore<Preferences> by preferencesDataStore(SVATECNIK_DATA_STORE_KEY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository, dataStore)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        setContentView(R.layout.activity_main)

        when (viewModel.isHomeFragmentShow.value) {
            true -> replaceFragment(homeFragment)
            false -> replaceFragment(favouritesFragment)
            else -> replaceFragment(homeFragment)
        }

        bottom_navbar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    viewModel.isHomeFragmentShow.value = true
                    replaceFragment(homeFragment)
                }
                R.id.ic_favourites -> {
                    viewModel.isHomeFragmentShow.value = false
                    replaceFragment(favouritesFragment)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}