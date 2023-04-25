package com.juanimozo.matchpoint.presentation.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.juanimozo.matchpoint.ui.theme.Shapes

@Composable
fun GenericButton(
    modifier: Modifier,
    color: Color,
    onClick: () -> Unit,
    text: String
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = Shapes.medium,
        colors = ButtonDefaults.buttonColors(backgroundColor = color)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button
        )
    }
}