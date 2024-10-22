package com.juanimozo.matchpoint.presentation.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.juanimozo.matchpoint.R
import com.juanimozo.matchpoint.ui.theme.LooserRed
import com.juanimozo.matchpoint.ui.theme.Size

@Composable
fun ActionsRow(
    height: Dp,
    width: Dp,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(height)
            .width(width)
            .clickable(onClick = onDelete),
        shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(LooserRed),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.ic_delete),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(Size.Icon.medium),
                tint = Color.White,
                contentDescription = "Delete action"
            )
        }
    }
}