package com.dukendev.mintlauncher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var isSplashLoaded = MutableStateFlow(false)
        private set

    init {
        viewModelScope.launch {
            delay(2000)
            isSplashLoaded.value = true
        }
    }

}