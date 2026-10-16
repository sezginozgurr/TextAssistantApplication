package com.textassistantapplication.uitoolkit

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.textassistantapplication.ui.theme.TextAssistantApplicationTheme

/** Height of full-size action buttons (Generate, Cancel, Share). */
val ActionButtonHeight = 52.dp

private val IconTextButtonIconSize = 18.dp

/** Filled button with a leading icon. Callers decide which icon and text to show. */
@Composable
fun IconTextButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(ActionButtonHeight),
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        colors = colors,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(IconTextButtonIconSize),
        )
        Spacer(Modifier.width(Spacing.Small))
        Text(text)
    }
}

@ThemedPreview
@Composable
private fun IconTextButtonPreview() {
    TextAssistantApplicationTheme {
        IconTextButton(text = "Generate", icon = Icons.AutoMirrored.Filled.Send, onClick = {})
    }
}

@ThemedPreview
@Composable
private fun IconTextButtonCustomColorsPreview() {
    TextAssistantApplicationTheme {
        IconTextButton(
            text = "Share",
            icon = Icons.Filled.Share,
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ),
        )
    }
}
