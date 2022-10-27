package org.d3if4097.mobpro2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.d3if4097.mobpro2.data.Mahasiswa
import org.d3if4097.mobpro2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainDialog.DialogListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            MainDialog().show(supportFragmentManager, "MainDialog")
        }
    }

    override fun processDialog(mahasiswa: Mahasiswa) {
        Log.d("MainActivity", mahasiswa.toString())
    }
}