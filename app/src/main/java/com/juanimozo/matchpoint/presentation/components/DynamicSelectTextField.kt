package com.juanimozo.matchpoint.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.juanimozo.matchpoint.R
import com.juanimozo.matchpoint.domain.model.PlayerModel
import com.juanimozo.matchpoint.ui.theme.Size

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DynamicSelectTextField(
    textFieldValue: String,
    options: List<PlayerModel>,
    label: String,
    onTextValueChange: (String) -> Unit,
    onValueChangedEvent: (PlayerModel) -> Unit,
    showAddNewPlayer: Boolean = true,
    onAddNewPlayerEvent: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.70f)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = textFieldValue,
                onValueChange = { query -> onTextValueChange(query) },
                leadingIcon = {
                    Icon(
                        painterResource(R.drawable.ic_search),
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        contentDescription = "Search player"
                    )
                },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.body2.copy(
                            color = Color.DarkGray
                        )
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                if (showAddNewPlayer) {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onAddNewPlayerEvent()
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painterResource(R.drawable.ic_add_player),
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .size(Size.Icon.medium),
                                contentDescription = "Add player"
                            )
                            Text("Agregar nuevo jugador",
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }

                options.forEachIndexed { index, option: PlayerModel ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onValueChangedEvent(option)
                        }
                    ) {
                        Column {
                            Text(
                                modifier = Modifier.padding(vertical = 4.dp),
                                text = option.name,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }
        }
    }
}