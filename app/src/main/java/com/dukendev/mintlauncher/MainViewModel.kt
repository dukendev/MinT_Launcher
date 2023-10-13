package com.dukendev.mintlauncher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dukendev.mintlauncher.data.event.GlobalNotificationItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var isSplashLoaded = MutableStateFlow(false)
        private set

    var notificationList = MutableStateFlow<MutableList<GlobalNotificationItem>>(mutableListOf())
        private set

    fun updateNotifications(update: GlobalNotificationItem) {
        notificationList.value.add(update)
    }

    init {
        viewModelScope.launch {
            delay(500)
            isSplashLoaded.value = true
        }
    }

}