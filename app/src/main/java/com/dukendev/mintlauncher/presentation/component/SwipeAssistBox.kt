package com.dukendev.mintlauncher.presentation.component

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.abs

@Composable
fun SwipeAssistBox(
    onLeft: () -> Unit = {},
    onDown: () -> Unit = {},
    onUp: () -> Unit = {},
    onRight: () -> Unit = {},
    content: @Composable () -> Unit
) {
    var direction by remember { mutableStateOf(-1) }

    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()

                        val (x, y) = dragAmount
                        if (abs(x) > abs(y)) {
                            when {
                                x > 0 -> {
                                    //right
                                    direction = 0
                                }

                                x < 0 -> {
                                    // left
                                    direction = 1
                                }
                            }
                        } else {
                            when {
                                y > 10 -> {
                                    // down
                                    direction = 2
                                }

                                y < -10 -> {
                                    // up
                                    direction = 3
                                }
                            }
                        }

                    },
                    onDragEnd = {
                        when (direction) {
                            0 -> {
                                onRight()
                            }

                            1 -> {
                                onLeft()
                                // left swipe code here
                            }

                            2 -> {
                                onDown()
                                // down swipe code here
                            }

                            3 -> {
                                onUp()
                                // up swipe code here
                            }
                        }
                    }
                )
            }

    ){
        content()
    }
}