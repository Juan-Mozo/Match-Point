package com.juanimozo.matchpoint.util

sealed class Points(val s: String) {
    class Zero(s: String = "0") : Points(s)
    class Fifteen(s: String = "15") : Points(s)
    class Thirty(s: String = "30") : Points(s)
    class Forty(s: String = "40") : Points(s)
    class AdIn(s: String = "Ad In") : Points(s)
    class AdOut(s: String = "Ad Out") : Points(s)
}