package com.juanimozo.matchpoint.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.juanimozo.matchpoint.ui.theme.Shapes

@Composable
fun GenericButton(
    modifier: Modifier,
    color: Color,
    onClick: () -> Unit,
    text: String,
    textColor: Color = Color.White
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = Shapes.medium,
        colors = ButtonDefaults.buttonColors(backgroundColor = color)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 8.dp),
            style = MaterialTheme.typography.button,
            color = textColor
        )
    }
}