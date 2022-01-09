package com.example.svatecnik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val alarmsFragment = AlarmsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)
        bottom_navbar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_alarms -> replaceFragment(alarmsFragment)
            }
            true
        }
        //bottom_navbar
    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment != null)
        {
            val transation = supportFragmentManager.beginTransaction()
            transation.replace(R.id.fragment_container, fragment)
            transation.commit()
        }
    }
}