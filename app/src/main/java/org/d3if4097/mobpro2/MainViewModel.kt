package org.d3if4097.mobpro2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if4097.mobpro2.model.Harian

class MainViewModel : ViewModel() {
    private val data = MutableLiveData<List<Harian>>()

    fun getData(): LiveData<List<Harian>> = data

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                requestData()
            }
        }
    }
    private suspend fun requestData() {
        try {
            val result = Covid19Api.service.getData()
            data.postValue(result.update.harian)
        }
        catch (e: Exception) {
            Log.d("REQUEST", e.message.toString())
        }
    }
}