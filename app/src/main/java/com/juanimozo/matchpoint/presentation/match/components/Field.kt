package com.juanimozo.matchpoint.presentation.match.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanimozo.matchpoint.domain.model.PlayerModel

@Composable
fun Field(
    title: String,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isListCollapsed: Boolean,
    players: List<PlayerModel>,
    onHeaderClick: Unit,
    onPlayerClick: (player: PlayerModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(vertical = 6.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1
            )
        }
        Row(modifier = Modifier.padding(top = 12.dp)) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            )
        }
        if (isListCollapsed) {
            Row (modifier = Modifier.padding(bottom = 12.dp)) {
                PlayersList(
                    players = players,
                    onHeaderClick = onHeaderClick,
                    onPlayerClick = onPlayerClick
                )
            }
        }
    }
}