package com.example.gpsapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine

class LocationService {

    @SuppressLint("MissingPermission")
    suspend fun getLocation(context: Context) : Location? {
        val fusedLocationProviderClient : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        // VALIDANDO PERMISO MANUALMENTE DE MOMENTO
        val isLocationPermissionGranted = true
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.GPS_PROVIDER
        )
        if (!isGpsEnabled || !isLocationPermissionGranted){
            return null
        }
        return suspendCancellableCoroutine { cont ->
        fusedLocationProviderClient.lastLocation.apply {
                if (isComplete){
                    if (isSuccessful){
                        cont.resume(result) {}
                    } else {
                        cont.resume(null) {}
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it) {}
                }
                addOnFailureListener{
                    cont.resume(null) {}
                }
                addOnCanceledListener {
                    cont.resume(null) {}
                }
            }
        }
    }
}