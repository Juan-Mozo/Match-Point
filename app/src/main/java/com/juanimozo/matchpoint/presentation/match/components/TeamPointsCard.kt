package com.juanimozo.matchpoint.presentation.match.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juanimozo.matchpoint.ui.theme.BackgroundWhite
import com.juanimozo.matchpoint.ui.theme.DarkGreen
import com.juanimozo.matchpoint.ui.theme.Fonts
import com.juanimozo.matchpoint.ui.theme.NavyBlue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TeamPointsCard(
    points: String,
    cardAction: () -> Unit,
    buttonAction: () -> Unit
) {
    Card(
        modifier = Modifier.padding(start = 16.dp, end = 8.dp),
        backgroundColor = NavyBlue,
        onClick = cardAction
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = points,
                style = TextStyle(
                    fontFamily = Fonts.MontserratSemiBold,
                    fontSize = 90.sp,
                    color = Color.White
                ),
                textAlign = TextAlign.Justify
            )
            OutlinedButton(
                modifier = Modifier
                    .size(45.dp)
                    .padding(bottom = 16.dp),
                onClick = buttonAction,
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGreen),
                border = BorderStroke(3.dp, NavyBlue)
            ) {
                Text(
                    text = "-",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}