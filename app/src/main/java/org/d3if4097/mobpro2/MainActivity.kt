package org.d3if4097.mobpro2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import org.d3if4097.mobpro2.data.Mahasiswa
import org.d3if4097.mobpro2.data.MahasiswaDb
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

    private val viewModel: MainViewModel by lazy {
        val dataSource = MahasiswaDb.getInstance(this).dao
        val factory = MainViewModelFactory(dataSource)
        ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    override fun processDialog(mahasiswa: Mahasiswa) {
        viewModel.insertData(mahasiswa)
    }
}