package com.juanimozo.matchpoint.presentation.match.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.juanimozo.matchpoint.R
import com.juanimozo.matchpoint.presentation.components.animation.AnimationType
import com.juanimozo.matchpoint.presentation.components.animation.VisibilityAnimation
import com.juanimozo.matchpoint.ui.theme.Size

@Composable
fun NewPlayerPopUp(
    showPopUp: Boolean,
    value: String,
    onClickOutside: () -> Unit,
    onTextValueChange: (String) -> Unit,
    onFinishButton: () -> Unit
) {
    VisibilityAnimation (showPopUp, AnimationType.ExpandAnimation(300)) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black.copy(0.6f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Popup(
                    alignment = Alignment.Center,
                    properties = PopupProperties(
                        excludeFromSystemGesture = true,
                        focusable = true
                    ),
                    onDismissRequest = { onClickOutside() }
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        elevation = 2.dp
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row (
                                modifier = Modifier.padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_tennis_player),
                                    modifier = Modifier.size(Size.Icon.medium),
                                    contentDescription = "Tennis player"
                                )
                                Text(
                                    text = "Nuevo jugador",
                                    style = MaterialTheme.typography.subtitle1
                                )
                            }
                            OutlinedTextField(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 32.dp),
                                value = value,
                                onValueChange = onTextValueChange,
                                label = {
                                    Text(
                                        text = "Nombre",
                                        style = MaterialTheme.typography.subtitle2
                                    )
                                }
                            )
                            Button(
                                modifier = Modifier.padding(vertical = 16.dp),
                                onClick = onFinishButton
                            ) {
                                Text(text = "Agregar")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewNewPlayerPopUp() {
    NewPlayerPopUp(
        showPopUp = true,
        value = "Jugador",
        onClickOutside = {},
        onTextValueChange = {_ -> },
        onFinishButton = {}
    )
}