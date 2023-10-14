package com.dukendev.mintlauncher.data.service

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import com.dukendev.mintlauncher.data.event.GlobalNotificationItem
import com.dukendev.mintlauncher.data.event.NotificationEvent
import org.greenrobot.eventbus.EventBus
import java.io.ByteArrayOutputStream


const val ACTION = "com.dukendev.mintluancher.NOTIFICATION_LISTENER_SERVICE_ACTION"
const val CMD_FETCH_ALL = "fetch-all-notification"
const val CMD_DELETE_ALL = "delete-all-notification"
const val NLS_CMD = "nls-cmd"

class NotificationListener : NotificationListenerService() {

    private val TAG = "notifs"

    private var nlservicereciver: NLServiceReceiver? = null
    override fun onCreate() {
        super.onCreate()
        nlservicereciver = NLServiceReceiver()
        val filter = IntentFilter()
        filter.addAction(ACTION)
        registerReceiver(nlservicereciver, filter)
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(nlservicereciver)

    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        Log.i(TAG, "**********  onNotificationPosted")
        Log.i(TAG, "ID :" + sbn.id + "\t" + sbn.notification.tickerText + "\t" + sbn.packageName)
        val i = Intent(ACTION)
        i.putExtra(
            "notification_event", """
     onNotificationPosted :${sbn.packageName}
     title : ${sbn.notification.tickerText} 
     content : ${sbn.notification.category} 
     """.trimIndent()
        )
        sendBroadcast(i)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.i(TAG, "********** onNOtificationRemoved")
        Log.i(TAG, "ID :" + sbn.id + "\t" + sbn.notification.tickerText + "\t" + sbn.packageName)
        val i = Intent(ACTION)
        i.putExtra(
            "notification_event", """
     onNotificationRemoved :${sbn.packageName}
     """.trimIndent()
        )
        sendBroadcast(i)
        EventBus.getDefault().post(
            NotificationEvent(
                notification =
                GlobalNotificationItem(
                    packageId = sbn.packageName,
                    appName = null,
                    appIcon = null,
                    title = null,
                    content = null,
                    subContent = null,
                    time = "", appDrawable = null
                )
            )

        )

    }

    override fun onListenerDisconnected() {
        requestRebind(ComponentName(this, NotificationListenerService::class.java))
    }

    inner class NLServiceReceiver : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.getStringExtra(NLS_CMD) == CMD_DELETE_ALL) {
                this@NotificationListener.cancelAllNotifications()
            } else if (intent.getStringExtra(NLS_CMD) == CMD_FETCH_ALL) {
                val i1 = Intent(ACTION)
                i1.putExtra("notification_event", "=====================")
                sendBroadcast(i1)
                var i = 1
                var appLabel = ""
                var appIcon: Icon? = null

                var appIcon2: Drawable? = null
                for (sbn in this@NotificationListener.activeNotifications) {

                    val notificationDetails = buildString {
                        append("$i. ")
                        val packageName = sbn.packageName
                        try {

                            val sbnContext = try {
                                createPackageContext(sbn.opPkg, 0)
                            } catch (e: Exception) {
                                context
                            }
                            val appInfo = sbnContext?.packageManager?.getApplicationInfo(
                                sbn.opPkg,
                                PackageManager.GET_META_DATA
                            )
                            appLabel = appInfo?.let {
                                sbnContext.packageManager?.getApplicationLabel(
                                    it
                                ).toString()
                            }.toString()
                            appIcon2 = appInfo?.let {
                                packageManager?.getApplicationIcon(
                                    it
                                )
                            }
                            appIcon = sbn.notification.smallIcon
                            append("App Name: $appLabel, ")
                            append("Package ID: $packageName, ")
                            append("Icon: $appIcon, ")
                        } catch (e: PackageManager.NameNotFoundException) {
                            append("Package ID: $packageName, ")
                        }

                        val tickerText = sbn.notification.tickerText?.toString() ?: "No Ticker Text"
                        append("Ticker Text: $tickerText, ")
                        append("Category: ${sbn.notification.category}, ")

                        val notificationChannel = sbn.notification.channelId
                        append("Channel: $notificationChannel, ")

                        val extras = sbn.notification.extras
                        val notificationTitle =
                            extras.getCharSequence(Notification.EXTRA_TITLE) ?: "No Title"
                        val notificationText =
                            extras.getCharSequence(Notification.EXTRA_TEXT) ?: "No Text"
                        val notificationSubText =
                            extras.getCharSequence(Notification.EXTRA_SUB_TEXT) ?: "No SubText"

                        append("Title: $notificationTitle, ")
                        append("Text: $notificationText, ")
                        append("SubText: $notificationSubText")


                    }

                    EventBus.getDefault().post(
                        NotificationEvent(
                            notification =
                            GlobalNotificationItem(
                                packageId = sbn.packageName,
                                appName = appLabel,
                                appIcon = sbn.notification.smallIcon,
                                title = sbn.notification.tickerText?.toString(),
                                content = sbn.notification.extras.getCharSequence(Notification.EXTRA_TITLE)
                                    .toString(),
                                subContent = sbn.notification.extras.getCharSequence(Notification.EXTRA_TEXT)
                                    .toString(),
                                time = sbn.notification.`when`.toString(),
                                appDrawable = appIcon2,
                                pendingIntent = sbn.notification.contentIntent
                            )

                        )
                    )

                    val i2 = Intent(ACTION)
                    i2.putExtra("notification_event", notificationDetails)
                    sendBroadcast(i2)


                    i++
                }

                val i3 = Intent(ACTION)
                i3.putExtra("notification_event", "===== Notification List ====")
                sendBroadcast(i3)
            }
        }

        private fun getStringFromBitmap(bitmapPicture: Bitmap): String? {
            val COMPRESSION_QUALITY = 100
            val encodedImage: String
            val byteArrayBitmapStream = ByteArrayOutputStream()
            bitmapPicture.compress(
                Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream
            )
            val b = byteArrayBitmapStream.toByteArray()
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
            return encodedImage
        }
    }
}