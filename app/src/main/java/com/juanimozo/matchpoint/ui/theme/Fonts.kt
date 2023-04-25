package com.juanimozo.matchpoint.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.juanimozo.matchpoint.R

class Fonts {

    companion object {

        val QuickSandRegular = FontFamily(
            Font(
                resId = R.font.quicksand_regular,
                weight = FontWeight.Normal,
                style = FontStyle.Normal
            )
        )

        val MontserratMedium = FontFamily(
            Font(
                resId = R.font.montserrat_medium,
                weight = FontWeight.Medium,
                style = FontStyle.Normal
            )
        )

        val MontserratRegular = FontFamily(
            Font(
                resId = R.font.montserrat_regular,
                weight = FontWeight.Normal,
                style = FontStyle.Normal
            )
        )

        val MontserratSemiBold = FontFamily(
            Font(
                resId = R.font.montserrat_semi_bold,
                weight = FontWeight.SemiBold,
                style = FontStyle.Normal
            )
        )

        val MontserratThin = FontFamily(
            Font(
                resId = R.font.montserrat_thin,
                weight = FontWeight.Thin,
                style = FontStyle.Normal
            )
        )
    }

}