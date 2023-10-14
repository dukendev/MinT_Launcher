package com.dukendev.mintlauncher.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dukendev.mintlauncher.presentation.component.HomeHeader
import com.dukendev.mintlauncher.presentation.component.HomePanel
import com.dukendev.mintlauncher.presentation.nav.Routes
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(true) {
        systemUiController.isNavigationBarVisible = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 8.dp, vertical = 32.dp)
    ) {
        HomeHeader()
        HomePanel {
            navController.navigate(Routes.Apps.name)
        }
    }

}