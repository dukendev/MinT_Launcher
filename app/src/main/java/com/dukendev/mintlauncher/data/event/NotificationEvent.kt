package com.dukendev.mintlauncher.data.event

import android.graphics.drawable.Drawable

data class NotificationEvent(
    val notification: GlobalNotificationItem,
)

data class GlobalNotificationItem(
    val packageId: String,
    val appName: String?,
    val appIcon: Drawable?,
    val title: String?,
    val content: String?,
    val subContent: String?,
    val time: String,
)