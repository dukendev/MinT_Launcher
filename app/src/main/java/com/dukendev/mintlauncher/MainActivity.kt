package com.dukendev.mintlauncher

import android.Manifest
import android.animation.ObjectAnimator
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.dukendev.mintlauncher.data.event.NotificationEvent
import com.dukendev.mintlauncher.data.service.ACTION
import com.dukendev.mintlauncher.data.service.CMD_FETCH_ALL
import com.dukendev.mintlauncher.data.service.NLS_CMD
import com.dukendev.mintlauncher.data.service.NotificationListener
import com.dukendev.mintlauncher.presentation.nav.MintNavGraph
import com.dukendev.mintlauncher.ui.theme.MinTLauncherTheme
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : ComponentActivity() {

    private lateinit var notificationReceiver: NotificationReceiver

    private val mainViewModel by viewModels<MainViewModel>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        val intent = Intent(this, NotificationListener::class.java).apply {
            action = ACTION
        }
        startService(intent)
        notificationReceiver = NotificationReceiver()
        val filter = IntentFilter()
        filter.addAction(ACTION)
        registerReceiver(notificationReceiver, filter)
        sendDummyNotification(this)



        setupSplash(mainViewModel)
        hideSystemUI()
        setContent {
            MinTLauncherTheme {
                val notifications by mainViewModel.notificationList.collectAsState()
                MintNavGraph(notifications) {
                    val i = Intent(ACTION)
                    i.putExtra(NLS_CMD, CMD_FETCH_ALL)
                    sendBroadcast(i)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(notificationReceiver)
        EventBus.getDefault().unregister(this)
    }


    private fun setupSplash(mainViewModel: MainViewModel) {
        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                !mainViewModel.isSplashLoaded.value
            }
            // Animation part
            this.setOnExitAnimationListener { sp ->
                sp.iconView.animate().duration = 1000L
                val slideUp = ObjectAnimator.ofFloat(
                    sp.iconView,
                    View.TRANSLATION_Y,
                    0f,
                    -sp.iconView.height.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 1000L
                slideUp.doOnEnd { sp.remove() }
                slideUp.start()
            }
        }
    }

    private fun hideSystemUI() {
        actionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    inner class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val temp = "${intent.getStringExtra("notification_event")}"
            Log.d("notifs", temp)
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun sendDummyNotification(context: Context) {
        val channelId = "my_channel_id"
        val notificationId = 1

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_term_arrow)
            .setContentTitle("Notification Title")
            .setContentText("This is a dummy notification.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Auto-dismiss the notification when the user taps it

        val notificationManager = NotificationManagerCompat.from(context)

        // Create a notification channel if the Android version is Oreo (API level 26) or higher
        val name = "My Channel"
        val descriptionText = "My Channel Description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                // showPermissionExplanationDialog()
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
        notificationManager.notify(notificationId, builder.build())
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNotificationEvent(event: NotificationEvent?) {
        // Do something
        if (event == null)
            return

        Log.d("bus", "${event.notification}")
        mainViewModel.updateNotifications(event.notification)
    }

}

