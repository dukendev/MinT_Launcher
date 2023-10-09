package com.dukendev.mintlauncher

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.dukendev.mintlauncher.ui.theme.MinTLauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel by viewModels<MainViewModel>()
        setupSplash(mainViewModel)
        hideSystemUI()
        setContent {
            MinTLauncherTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    Text(text = ".minT", style = TextStyle(color = Color.White))
                }
            }
        }
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

}

