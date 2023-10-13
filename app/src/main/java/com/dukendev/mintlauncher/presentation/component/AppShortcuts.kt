package com.dukendev.mintlauncher.presentation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun AppShortcuts() {

    val value by rememberInfiniteTransition().animateFloat(
        initialValue = 0.8f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearOutSlowInEasing)
        )
    )


    val value2 by rememberInfiniteTransition().animateFloat(
        initialValue = 1.2f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing)
        )
    )


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 32.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Recent Apps", style = MaterialTheme.typography.headlineSmall)
            Box {

                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Heart",
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .graphicsLayer {
                            scaleX *= value
                            scaleY *= value
                        }, tint = MaterialTheme.colorScheme.primary
                )
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Heart",
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .graphicsLayer {
                            scaleX *= value2
                            scaleY *= value2
                        }, tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            }


        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RecentApps()
            FavouriteApps()
        }
    }
}

val recentMock = listOf("Youtube", "Google", "Reddit", "Instagram")

@Composable
fun RecentApps() {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
    ) {
        items(recentMock.size) {
            RecentAppItem(mock = recentMock[it])
        }
    }
}

@Composable
fun RecentAppItem(mock: String) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = mock, style = MaterialTheme.typography.bodyLarge)
        Text(text = "2m ago", style = MaterialTheme.typography.bodySmall)

    }
}


@Composable
fun FavouriteApps() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
    ) {
        items(recentMock.size) {
            FavouriteAppItem()
        }
    }
}

@Composable
fun FavouriteAppItem() {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(16.dp))
            .border(width = 0.dp, color = Color.Transparent, shape = RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .clickable { }
            .padding(8.dp)
    ) {
        Icon(
            Icons.Default.ThumbUp,
            contentDescription = null,
        )
    }
}
