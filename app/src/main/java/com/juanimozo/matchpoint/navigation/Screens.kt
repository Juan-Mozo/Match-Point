package com.juanimozo.matchpoint.navigation

sealed class Screens(val route: String) {
    object Main : Screens(route = "main")
    object NewMatch : Screens(route = "new-match")
    object History : Screens(route = "history")
    object CurrentMatch : Screens(route = "current-match")
    object Result : Screens(route = "result?isNewMatch={isNewMatch}")
}
