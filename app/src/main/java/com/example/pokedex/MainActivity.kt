package com.example.pokedex

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pokedex.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private var drawerState = false
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.close_app -> {
                    finish()
                    exitProcess(0)
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            android.R.id.home -> {
                if(navController.currentDestination!!.id == R.id.pokemonListFragment) {
                    if(!drawerState) binding.drawerLayout.openDrawer(GravityCompat.START)
                    else binding.drawerLayout.closeDrawer(GravityCompat.START)
                    drawerState = !drawerState
                }else{
                    navController.popBackStack()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}