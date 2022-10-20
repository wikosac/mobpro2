package org.d3if4097.mobpro2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.d3if4097.mobpro2.model.Provinsi

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap

    private val viewModel: MapsViewModel by lazy {
        ViewModelProvider(this).get(MapsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.getData().observe(this, { data ->
            data.forEach { createMarker(it) }
        })
    }

    override fun onMapReady(gMap: GoogleMap) {
        map = gMap
        viewModel.requestData()

        val lokasi = LatLng(-6.920432082789247, 107.60370834146391)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasi, 7f))
    }

    private fun createMarker(prov: Provinsi) {
        val lokasi = LatLng(prov.lokasi.lat, prov.lokasi.lon)
        val snippet = getString(R.string.jumlah_dirawat, prov.dirawat)
        map.addMarker(
            MarkerOptions()
            .position(lokasi)
            .title(prov.key)
            .snippet(snippet))
    }
}