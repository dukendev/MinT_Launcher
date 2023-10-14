package com.dukendev.mintlauncher.presentation.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dukendev.mintlauncher.data.event.GlobalNotificationItem
import com.dukendev.mintlauncher.presentation.apps.AppDrawerScreen
import com.dukendev.mintlauncher.presentation.home.HomeScreen
import com.dukendev.mintlauncher.presentation.root.RootScreen

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun MintNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Root.name) {
        composable(Routes.Root.name) {
            RootScreen(navController = navController){
            }
        }
        composable(Routes.Home.name) {
            HomeScreen(navController)
        }
        composable(Routes.Apps.name) {
            AppDrawerScreen(navController)
        }
    }
}