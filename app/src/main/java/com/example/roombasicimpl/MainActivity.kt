package com.example.roombasicimpl

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.roombasicimpl.databinding.ActivityMainBinding
import com.example.roombasicimpl.presentation.ContactFormFragment
import com.example.roombasicimpl.presentation.ContactSearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var toolBar:Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        toolBar = binding.toolbar

        setupBottomNavBar()
        setupDefaultToolbar()
    }

    private fun toolbarSearchOnClick(){
        navController.navigate(R.id.contactSearchFragment)
    }

    private fun toolbarAddContactOnClick(){
        navController.navigate(R.id.contactFormFragment)
    }

    private fun setupDefaultToolbar(){
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.searchButton.setOnClickListener{ toolbarSearchOnClick() }
        binding.addContactButton.setOnClickListener{toolbarAddContactOnClick()}
    }

    private fun setupCallHistoryToolbar(){
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.searchButton.setOnClickListener{ toolbarSearchOnClick() }
        binding.addContactButton.setOnClickListener{toolbarAddContactOnClick()}
    }

    private fun setupBottomNavBar(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.setupWithNavController(navController)
    }

}