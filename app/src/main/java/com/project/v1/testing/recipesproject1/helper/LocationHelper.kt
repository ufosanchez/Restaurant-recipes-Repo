package com.project.v1.testing.recipesproject1.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class LocationHelper private constructor() {

    private val TAG = "LOCATION-HELPER"

    companion object {
        val instance = LocationHelper()
    }

    var locationPermissionGranted = false

    val REQUEST_CODE_LOCATION = 1234

    init {
    }

    fun hasFineLocationPermission(context: Context): Boolean {

        if (ContextCompat.checkSelfPermission(context.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            return false
        }
    }

    fun hasCoarseLocationPermission(context: Context): Boolean {

        return ContextCompat.checkSelfPermission(context.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissions(context: Context) {

        if (hasFineLocationPermission(context) == true && hasCoarseLocationPermission(context) == true) {
            this.locationPermissionGranted = true
        }
        Log.d(TAG, "checkPermissions: Are location permissions granted? : " + this.locationPermissionGranted)

        if (this.locationPermissionGranted == false) {
            Log.d(TAG, "checkPermissions: Permissions not granted, so requesting permission now...")
            requestLocationPermission(context)
        }
    }

    fun requestLocationPermission(context: Context) {

        val listOfRequiredPermission
                = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

        ActivityCompat.requestPermissions((context as Activity), listOfRequiredPermission, REQUEST_CODE_LOCATION)
    }

}