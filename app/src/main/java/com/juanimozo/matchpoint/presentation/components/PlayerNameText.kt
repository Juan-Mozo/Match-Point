package com.juanimozo.matchpoint.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PlayerNameText(paddingTop: Dp = 0.dp, name: String, isWinner: Boolean) {
    val style = if (isWinner) { MaterialTheme.typography.body2 } else { MaterialTheme.typography.body1 }

    Text(
        modifier = Modifier.padding(top = paddingTop),
        text = name,
        style = style,
        maxLines = 1
    )
}