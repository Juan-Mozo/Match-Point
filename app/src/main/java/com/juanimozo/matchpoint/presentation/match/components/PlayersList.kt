package com.juanimozo.matchpoint.presentation.match.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanimozo.matchpoint.domain.model.PlayerModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlayersList(
    players: List<PlayerModel>,
    onHeaderClick: Unit,
    onPlayerClick: (player: PlayerModel) -> Unit,
) {
    val itemHeight = 45.dp

    // Players List
    LazyColumn(
        modifier = Modifier
            .height(itemHeight.times(3))
            .fillMaxWidth()
    ) {
        // Add new player
        item {
            Card(
                modifier = Modifier.height(itemHeight),
                    onClick = { onHeaderClick }) {
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Icon(Icons.Filled.Add, contentDescription = "Add player")
                    Text("Agregar nuevo jugador", style = MaterialTheme.typography.body2)
                }
            }
        }
        items(players) { player ->
            Card(
                modifier = Modifier.height(itemHeight),
                onClick = { onPlayerClick(player) })
            {
                Text(player.name, style = MaterialTheme.typography.body1)
            }
        }
    }
}