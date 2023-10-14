package com.dukendev.mintlauncher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dukendev.mintlauncher.data.event.GlobalNotificationItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var isSplashLoaded = MutableStateFlow(false)
        private set

//    private var notificationList = MutableStateFlow<MutableSet<GlobalNotificationItem>>(mutableSetOf())
//    val notifications : StateFlow<MutableSet<GlobalNotificationItem>> = notificationList.asStateFlow()
//
//    fun updateNotifications(update: GlobalNotificationItem) {
//        notificationList.value.add(update)
//    }

    init {
        viewModelScope.launch {
            delay(500)
            isSplashLoaded.value = true
        }
    }

}