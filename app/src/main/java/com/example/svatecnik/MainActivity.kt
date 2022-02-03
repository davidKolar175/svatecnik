package com.example.svatecnik

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.svatecnik.repository.Repository
import com.example.svatecnik.utils.Constants.Companion.FAVOURITE_NAME_KEY
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val homeFragment = HomeFragment()
    private val alarmsFragment = AlarmsFragment()
    private val dataStore: DataStore<Preferences> by preferencesDataStore(FAVOURITE_NAME_KEY)

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
        replaceFragment(homeFragment)
        bottom_navbar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_alarms -> replaceFragment(alarmsFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transation = supportFragmentManager.beginTransaction()
            transation.replace(R.id.fragment_container, fragment)
            transation.commit()
        }
    }
}