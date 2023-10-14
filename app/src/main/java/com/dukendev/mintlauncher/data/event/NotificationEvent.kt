package com.dukendev.mintlauncher.data.event

import android.app.PendingIntent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon

data class NotificationEvent(
    val notification: GlobalNotificationItem,
)

data class GlobalNotificationItem(
    val packageId: String,
    val appName: String?,
    val appIcon: Icon?,
    val appDrawable : Drawable?,
    val title: String?,
    val content: String?,
    val subContent: String?,
    val time: String,
    val pendingIntent: PendingIntent? = null
)