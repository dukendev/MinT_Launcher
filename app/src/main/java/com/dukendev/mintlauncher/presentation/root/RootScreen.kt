package com.dukendev.mintlauncher.presentation.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dukendev.mintlauncher.presentation.nav.Routes

@Composable
fun RootScreen(navController: NavController, onFetch : () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Button(onClick = { navController.navigate(Routes.Home.name) }) {
                Text(text = "Home")
            }
            Button(onClick = { onFetch() }) {
                Text(text = "send notification")
            }

        }

    }
}