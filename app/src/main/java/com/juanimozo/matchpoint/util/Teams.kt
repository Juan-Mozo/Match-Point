package com.juanimozo.matchpoint.util

import androidx.compose.ui.graphics.Color
import com.juanimozo.matchpoint.ui.theme.NavyBlue

sealed class Teams() {
    class Team1(): Teams()
    class Team2(): Teams()

    companion object {

        fun getTeamColor(team: Teams, winnerTeam: Teams? = null): Color {
            val color: Color = if (winnerTeam != null) {
                when (winnerTeam) {
                    is Team1 -> { if (team is Team1) { NavyBlue } else { Color.Gray }}
                    is Team2 -> { if (team is Team2) { NavyBlue } else { Color.Gray }}
                }
            } else {
                NavyBlue
            }

            return color
        }

        fun convertTeamToInt(v: Teams?): Int {
            // When the winner is team 1 return 1 and when is team 2 return 2
            return if (v != null) {
                if (v is Team1) {
                    1
                } else {
                    2
                }
            } else {
                0
            }
        }

        fun convertIntToTeam(v: Int?): Teams? {
            return if (v != 0) {
                if (v == 1) {
                    Team1()
                } else {
                    Team2()
                }
            } else {
                return null
            }
        }

    }
}
