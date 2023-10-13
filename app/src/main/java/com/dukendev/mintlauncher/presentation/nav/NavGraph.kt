package com.dukendev.mintlauncher.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dukendev.mintlauncher.data.event.GlobalNotificationItem
import com.dukendev.mintlauncher.presentation.apps.AppDrawerScreen
import com.dukendev.mintlauncher.presentation.home.HomeScreen
import com.dukendev.mintlauncher.presentation.root.RootScreen

@Composable
fun MintNavGraph(notifications : List<GlobalNotificationItem>,onFetch : () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Root.name) {
        composable(Routes.Root.name) {
            RootScreen(navController = navController){
                onFetch()
            }
        }
        composable(Routes.Home.name) {
            HomeScreen(navController,notifications)
        }
        composable(Routes.Apps.name) {
            AppDrawerScreen(navController)
        }
    }
}