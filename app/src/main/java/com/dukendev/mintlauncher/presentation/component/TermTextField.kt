package com.dukendev.mintlauncher.presentation.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

@Composable
fun TermTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = TextStyle(
        color = Color.White, // Text color (white)
        fontFamily = FontFamily.Monospace, // Monospaced font for terminals
        fontSize = 16.sp // Adjust font size as needed
    ),
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = androidx.compose.ui.text.input.ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions(
        onDone = {
            // Handle "Done" action if needed
        }
    ),
) {
    val colors = TextFieldDefaults.colors(
        cursorColor = Color.White, // Cursor color (white)
        focusedIndicatorColor = Color.Transparent, // Remove the focus indicator
        unfocusedIndicatorColor = Color.Transparent, // Remove the unfocused indicator
        focusedTextColor = Color.White,
        focusedContainerColor = Color.Black
    )

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = colors,
        prefix = {
            Text(text = "usr>")
        }
    )
}