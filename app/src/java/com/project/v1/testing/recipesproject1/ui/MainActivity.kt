package com.project.v1.testing.recipesproject1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.project.v1.testing.recipesproject1.R
import com.project.v1.testing.recipesproject1.databinding.ActivityMainBinding
import com.project.v1.testing.recipesproject1.databinding.ActivityMainDrawerLayoutBinding
import com.project.v1.testing.recipesproject1.singletonsources.UserDataSource

class MainActivity : AppCompatActivity() {

    //lateinit var binding:ActivityMainBinding

    private val TAG = this@MainActivity.toString()

    private lateinit var binding:ActivityMainDrawerLayoutBinding

    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        //binding = ActivityMainBinding.inflate(layoutInflater)

        binding = ActivityMainDrawerLayoutBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // TODO: Get a reference to our Toolbar
        val toolBar:MaterialToolbar = binding.toolbarDrawer

        // TODO: Set the MaterialToolbar as the Activity's ActionBar.
        setSupportActionBar(toolBar)

        // TODO: The Below code will add an Up Button to All our destinations excluding our Start Destination
        //   Also, this will Add the Destination's Label to the Toolbar

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_frag_container_drawer) as NavHostFragment

        // TODO: Get the NavController Object associated with this Navigation Host which in our case is the built-in
        //   NavHostFragment
        val navController = navHostFragment.navController

        // TODO: The below code uses the Navigation Graph to add the Up button to all the destinations except the
        //   start destination. The below code also adds the Destination's title as defined in the Navigation Graph
        //   for each fragment/destination
        val builder = AppBarConfiguration.Builder(navController.graph)


        /*
           We need to use the NavigationDrawer. For this,we need to get a reference of our DrawerLayout
         */
        val navDrawer = binding.drawerLayout


        // TODO: Add the Navigation Drawer to the AppBarConfiguration Object to add the Drawer(Burger) Icon on the
        //  Tool Bar. This also allows the toolbar to add the drawer icon one every screen that does not have the
        //  up button
        builder.setOpenableLayout(navDrawer)

        //Create an AppBarConfiguration Object
        val appBarConfiguration = builder.build()

        // TODO: Setup our Toolbar for use with the NavigationController and our AppBarConfiguration Object
        toolBar.setupWithNavController(navController, appBarConfiguration)

        // TODO: Link the DrawerLayout with the NavigationController so that when the user clicks on the
        //   menu items in the Navigation Drawer, the NavigationController can display the right destination(Fragment)
        //   in the Navigation Host, i.e. the NavHostFragment in our case

        navView = binding.navView

        // TODO: This link's the Layout Drawer's Navigation View with the NavigationController
        navView.setupWithNavController(navController)

//        navView.setNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.sign_in ->{
//
//                    Log.d(TAG, "User tapped on Login Menu Item")
//
//                    true
//                }
//                else -> {
//                    false
//                }
//            }
//        }

    }


}