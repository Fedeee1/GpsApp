package com.example.gpsapp.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gpsapp.R
import com.example.gpsapp.commons.ANIMATION_MAP_ZOOM_DURATION
import com.example.gpsapp.commons.CITY_ZOOM
import com.example.gpsapp.commons.LATITUDE_KEY
import com.example.gpsapp.commons.LONGITUDE_KEY
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var latitude = 0.0
    private var longitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        initVars()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapCurrentLocation) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    private fun initVars(){
        latitude = intent.getDoubleExtra(LATITUDE_KEY, 0.0)
        longitude = intent.getDoubleExtra(LONGITUDE_KEY, 0.0)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getLocation(latitude,longitude)
    }

    private fun getLocation(lat: Double, long: Double){
        val coordinates = LatLng(lat, long)
        val city = MarkerOptions().position(coordinates)
        map.addMarker(city)
        val zoom: Float = CITY_ZOOM
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, zoom),
            ANIMATION_MAP_ZOOM_DURATION, null)
    }
}

