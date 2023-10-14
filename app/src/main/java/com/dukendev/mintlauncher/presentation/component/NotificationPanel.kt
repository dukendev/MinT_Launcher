package com.dukendev.mintlauncher.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dukendev.mintlauncher.data.event.GlobalNotificationItem
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun NotificationPanel(notifications: List<GlobalNotificationItem>) {

    TypewriterText(texts = notifications.map { "${it.content}  ${it.subContent}" })
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {


        items(notifications.size) {


            Image(
                rememberDrawablePainter(
                    drawable = notifications.elementAt(it).appDrawable
                ),
                contentDescription = "content description",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {


                    }
            )
        }
    }
}