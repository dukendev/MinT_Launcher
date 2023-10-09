package com.dukendev.mintlauncher.presentation.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dukendev.mintlauncher.util.DateUtil.Companion.formatDateWithDayOfMonthSuffix
import com.dukendev.mintlauncher.util.DateUtil.Companion.toTimeString
import kotlinx.coroutines.delay
import java.util.Date

@Composable
fun HomeHeader() {

    var sysTime by remember { mutableStateOf(Date()) }
    val date by remember { derivedStateOf { sysTime.formatDateWithDayOfMonthSuffix()} }
    val time by remember { derivedStateOf { sysTime.toTimeString()} }
    //Todo 1 : Move this to viewModel and have multiple time formats
    LaunchedEffect(true) {
        while (true) {
            sysTime = Date()
            delay(1000) // Update every second
        }
    }
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color.Black)
            .padding(horizontal = 8.dp, vertical = 48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {

        Column(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(4.dp)
        ) {
            Text(text = date, style = typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(text = time, style = typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
        IconButton(
            onClick = { }, modifier = Modifier
                .wrapContentSize()
        ) {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
        }
    }


}