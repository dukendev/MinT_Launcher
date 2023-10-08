package com.dukendev.mintlauncher

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dukendev.mintlauncher.ui.theme.MinTLauncherTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel by viewModels<MainViewModel>()
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
        setContent {
            MinTLauncherTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(Color.Transparent)
                Box(modifier = Modifier.fillMaxSize()){
                    Text(text = ".minT", modifier = Modifier.background(Color.White))
                }
            }
        }
    }
}

