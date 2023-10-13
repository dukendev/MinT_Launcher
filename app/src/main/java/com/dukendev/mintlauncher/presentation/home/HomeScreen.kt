package com.dukendev.mintlauncher.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dukendev.mintlauncher.data.event.GlobalNotificationItem
import com.dukendev.mintlauncher.presentation.component.HomeHeader
import com.dukendev.mintlauncher.presentation.component.HomePanel
import com.dukendev.mintlauncher.presentation.component.TypewriterText
import com.dukendev.mintlauncher.presentation.nav.Routes
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun HomeScreen(navController: NavController, notifications: List<GlobalNotificationItem>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 8.dp, vertical = 32.dp)
    ) {
        HomeHeader()
        TypewriterText(texts = notifications.map { "${it.content} ${it.title} ${it.subContent}" })
        LazyRow {
            items(notifications.size) {
                Image(
                    rememberDrawablePainter(notifications[it].appIcon),
                    contentDescription = "content description",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        HomePanel {
            navController.navigate(Routes.Apps.name)
        }
    }

}