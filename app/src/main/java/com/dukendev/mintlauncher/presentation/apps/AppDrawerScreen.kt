package com.dukendev.mintlauncher.presentation.apps


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dukendev.mintlauncher.presentation.component.SwipeAssistBox
import com.dukendev.mintlauncher.presentation.nav.Routes

@Composable
fun AppDrawerScreen(navController: NavController) {

    val list = remember {
        mutableStateOf(List(100) {
            it
        })
    }

    SwipeAssistBox(onDown = {
        navController.navigate(Routes.Home.name)
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            LazyColumn {
                items(list.value.size) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(16.dp)
                            .background(
                                Color.White.copy(alpha = 0.2f)
                            )
                    ) {
                        Text(text = list.value[it].toString())
                    }

                }
            }
        }
    }

}