package com.juanimozo.matchpoint.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juanimozo.matchpoint.R

@Composable
fun CustomTopBar(
    modifier: Modifier = Modifier,
    title: String,
    navigateUp: () -> Unit
) {
    Box(modifier = modifier) {
        Icon(
            painterResource(R.drawable.ic_arrow_left_circle),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(8.dp)
                .clickable { navigateUp() },
            contentDescription = "Navigate up"
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 8.dp),
            text = title,
            style = MaterialTheme.typography.h2.copy(
                fontSize = 28.sp
            )
        )
    }
}