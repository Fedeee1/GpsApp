package com.example.gpsapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.gpsapp.commons.LATITUDE_KEY
import com.example.gpsapp.commons.LONGITUDE_KEY
import com.example.gpsapp.databinding.ActivityMainBinding
import com.example.gpsapp.ui.map.MapsActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private val locationService: LocationService = LocationService()
    private var latitude = 0.0
    private var longitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCurrentLocation()
        binding.btnRefreshLocation.setOnClickListener {
            getCurrentLocation()
        }
        binding.btnOpenMap.setOnClickListener {
            if (latitude != 0.0 && longitude != 0.0) {
                openMap()
            } else {
                Toast.makeText(this, "No se encontró la ubicación", Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun getCurrentLocation()  {
        lifecycleScope.launch {
            val result = locationService.getLocation(this@MainActivity)
            if (result != null) {
                latitude = result.latitude
                longitude = result.longitude
                binding.txtLocation.text = "${result.latitude} ${result.longitude}"
            }
        }
    }
    private fun openMap(){
        val intent = Intent(this, MapsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(LATITUDE_KEY, latitude)
        intent.putExtra(LONGITUDE_KEY, longitude)
        startActivity(intent)
    }
}