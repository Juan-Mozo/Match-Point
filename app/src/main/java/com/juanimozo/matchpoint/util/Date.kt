package com.juanimozo.matchpoint.util

import java.text.SimpleDateFormat
import java.util.*

class Date {

    private val dayAndHour = SimpleDateFormat("dd MMMM yyyy HH:mm")
    private val dayMonthAndYear = SimpleDateFormat("dd MMMM yyyy")

    fun getDate(): String {
        val time = Calendar.getInstance().time
        return dayAndHour.format(time)
    }

    fun getSimplifiedDate(): String {
        val time = Calendar.getInstance().time
        return dayMonthAndYear.format(time)
    }

}