package com.project.v1.testing.recipesproject1.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.project.v1.testing.recipesproject1.databinding.ActivityLocationBinding
import com.project.v1.testing.recipesproject1.helper.LocationHelper
import com.squareup.picasso.Picasso

class LocationActivity : AppCompatActivity() {
    private val TAG = this.javaClass.canonicalName
    private lateinit var binding: ActivityLocationBinding

    private lateinit var locationHelper: LocationHelper

    private val args : LocationActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenMap.setOnClickListener {
            this.openMap()
        }

        this.locationHelper = LocationHelper.instance

        this.locationHelper.checkPermissions(this)

        if (this.locationHelper.locationPermissionGranted == true) {
            val snack = Snackbar.make(binding.clLayout,"All required permissions granted.", Snackbar.LENGTH_SHORT)
            snack.show()
        }
        else {
            val snack = Snackbar.make(binding.clLayout,"Warning: Missing Permissions.", Snackbar.LENGTH_SHORT)
            snack.show()
        }

        Picasso.get().load(args.photo).into(binding.imgRestaurant);
        binding.tvNameRestaurant.setText(args.name)
        binding.tvLocationRestaurant.setText(args.locationString)
        binding.tvAddressRestaurant.setText(args.address)
        binding.tvPriceRestaurant.setText(args.priceLevel)
        binding.btnPhoneRestaurant.setText(args.phone)
        binding.btnUrlRestaurant.setText(args.website)

        Log.d(TAG, "onCreate: Name is : ${args.name}")
        Log.d(TAG, "onCreate: Lat is : ${args.latitude}")
        Log.d(TAG, "onCreate: Lng is : ${args.longitude}")
        Log.d(TAG, "onCreate: Location string is : ${args.locationString}")
        Log.d(TAG, "onCreate: Photo is : ${args.photo}")
        Log.d(TAG, "onCreate: Phone is : ${args.phone}")
        Log.d(TAG, "onCreate: Address is : ${args.address}")
        Log.d(TAG, "onCreate: Website is : ${args.website}")
        Log.d(TAG, "onCreate: Price_level is : ${args.priceLevel}")
    }

    override fun onResume() {
        super.onResume()
        if (this.locationHelper.hasFineLocationPermission(this) == true && this.locationHelper.hasCoarseLocationPermission(this) == true) {
            binding.btnOpenMap.isEnabled = true
        }
        else{
            binding.btnOpenMap.isEnabled = false
        }

        binding.btnUrlRestaurant.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(args.website))
            startActivity(browserIntent)
        }

        binding.btnPhoneRestaurant.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + args.phone)
            startActivity(dialIntent)
        }
    }

    private fun openMap() {
        Log.d(TAG, "onClick: Open map to show location")
        val mapIntent= Intent(this, MapsActivity::class.java)
        mapIntent.putExtra("EXTRA_LAT", args.latitude.toDouble())
        mapIntent.putExtra("EXTRA_LNG", args.longitude.toDouble())
        startActivity(mapIntent)
    }

    private fun doReverseGeocoding() {
        Log.d(TAG, "onClick: Perform reverse geocoding to get coordinates from address.")
    }
}