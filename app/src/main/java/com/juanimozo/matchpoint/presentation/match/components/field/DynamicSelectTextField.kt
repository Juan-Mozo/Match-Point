package com.juanimozo.matchpoint.presentation.match.components.field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.juanimozo.matchpoint.domain.model.PlayerModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DynamicSelectTextField(
    textFieldValue: String,
    options: List<PlayerModel>,
    label: String,
    onTextValueChange: (String) -> Unit,
    onValueChangedEvent: (PlayerModel) -> Unit,
    onAddNewPlayerEvent: () -> Unit
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
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.body2
                    )
                }
            )

            ExposedDropdownMenu(
            //    modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onAddNewPlayerEvent()
                    }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            contentDescription = "Add player"
                        )
                        Text("Agregar nuevo jugador",
                            style = MaterialTheme.typography.body2
                        )
                    }
                }

                options.forEach { option: PlayerModel ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onValueChangedEvent(option)
                        }
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = option.name,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}