package com.juanimozo.matchpoint.util

import androidx.compose.ui.graphics.Color
import com.juanimozo.matchpoint.ui.theme.LooserRed
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.ui.theme.WinnerGreen

sealed class Teams(val int: Int) {
    class Team1(int: Int = 1): Teams(int)
    class Team2(int: Int = 2): Teams(int)

    companion object {

        fun getTeamColor(team: Teams, winnerTeam: Teams? = null): Color {
            val color: Color = if (winnerTeam != null) {
                when (winnerTeam) {
                    is Team1 -> { if (team == Team1()) { WinnerGreen } else { LooserRed }}
                    is Team2 -> { if (team == Team2()) { WinnerGreen } else { LooserRed }}
                }
            } else {
                NavyBlue
            }

            return color
        }

        fun convertTeamToInt(v: Teams?): Int {
            // When the winner is team 1 return 1 and when is team 2 return 2
            return if (v != null) {
                if (v == Team1()) {
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
