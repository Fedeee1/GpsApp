package com.example.gpsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.gpsapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val locationService: LocationService = LocationService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCurrentLocation()
        binding.btnRefreshLocation.setOnClickListener {
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation(){
        lifecycleScope.launch {
            val result = locationService.getLocation(this@MainActivity)
            if (result != null) {
                binding.txtLocation.text = "${result.latitude} ${result.longitude}"
            }
        }
    }
}