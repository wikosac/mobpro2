package org.d3if4097.mobpro2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if4097.mobpro2.model.Provinsi

class MapsViewModel : ViewModel() {
    private val data = MutableLiveData<List<Provinsi>>()

    fun requestData() = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.IO) {
            try {
                val result = Covid19Api.service.getProvinsi()
                data.postValue(result.data)
            } catch (e: Exception) {
                Log.d("MAPS", e.message.toString())
            }
        }
    }

    fun getData(): LiveData<List<Provinsi>> = data
}