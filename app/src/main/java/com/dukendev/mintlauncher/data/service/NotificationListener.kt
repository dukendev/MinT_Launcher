package com.dukendev.mintlauncher.data.service

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.dukendev.mintlauncher.data.event.GlobalNotificationItem
import com.dukendev.mintlauncher.data.event.NotificationEvent
import org.greenrobot.eventbus.EventBus


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
                    time = ""
                )
            )

        )

    }

    override fun onListenerDisconnected() {
        requestRebind(ComponentName(this, NotificationListenerService::class.java))
    }

    inner class NLServiceReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.getStringExtra(NLS_CMD) == CMD_DELETE_ALL) {
                this@NotificationListener.cancelAllNotifications()
            } else if (intent.getStringExtra(NLS_CMD) == CMD_FETCH_ALL) {
                val i1 = Intent(ACTION)
                i1.putExtra("notification_event", "=====================")
                sendBroadcast(i1)
                var i = 1
                var appLabel = ""
                var appIcon: Drawable? = null
                for (sbn in this@NotificationListener.activeNotifications) {
                    val sbnContext = createPackageContext(packageName, 0)
                    val notificationDetails = buildString {
                        append("$i. ")
                        val packageName = sbn.packageName
                        try {
                            val appInfo = sbnContext.packageManager.getApplicationInfo(
                                packageName,
                                PackageManager.GET_META_DATA
                            )
                            appLabel = sbnContext.packageManager.getApplicationLabel(
                                appInfo
                            ).toString()

                            appIcon = sbn.notification.smallIcon.loadDrawable(sbnContext)
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
                                appIcon = appIcon,
                                title = sbn.notification.tickerText?.toString(),
                                content = sbn.notification.extras.getCharSequence(Notification.EXTRA_TITLE)
                                    .toString(),
                                subContent = sbn.notification.extras.getCharSequence(Notification.EXTRA_TEXT)
                                    .toString(),
                                time = sbn.notification.`when`.toString(),
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
    }
}