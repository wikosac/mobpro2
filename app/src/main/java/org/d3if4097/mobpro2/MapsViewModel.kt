package org.d3if4097.mobpro2

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapsViewModel : ViewModel() {
    fun requestData() = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.IO) {
            try {
                val result = Covid19Api.service.getProvinsi()
                Log.d("MAPS", "Jumlah data: " + result.data.size)
            } catch (e: Exception) {
                Log.d("MAPS", e.message.toString())
            }
        }
    }
}