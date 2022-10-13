package org.d3if4097.mobpro2

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val authState = FirebaseUserLiveData()
}