package com.example.capsule_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.example.capsule_app.fragments.DashboardFragment

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentContainer : FragmentContainerView
    private lateinit var fragmentManager : FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentContainer=findViewById(R.id.fragmentContainerView)
        fragmentManager=supportFragmentManager

        val dashboardFragment=DashboardFragment()
        fragmentManager.beginTransaction().add(R.id.fragmentContainerView,dashboardFragment).commit()
    }
}