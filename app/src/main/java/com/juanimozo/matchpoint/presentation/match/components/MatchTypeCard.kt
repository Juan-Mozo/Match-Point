package com.juanimozo.matchpoint.presentation.match.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juanimozo.matchpoint.ui.theme.DarkGreen
import com.juanimozo.matchpoint.ui.theme.LightNavyBlue
import com.juanimozo.matchpoint.ui.theme.NavyBlue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MatchTypeCard(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) { DarkGreen } else { LightNavyBlue }
    val typography = if (isSelected) {
        MaterialTheme.typography.subtitle1.copy(color = Color.White)
    } else {
        MaterialTheme.typography.subtitle1.copy(color = NavyBlue)
    }

    Card(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = backgroundColor,
        shape = RectangleShape,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = text,
                style = typography
            )
        }
    }
}

@Preview
@Composable
fun MatchTypeCardPreview() {
    MatchTypeCard(text = "Singles", isSelected = true) {

    }
}