package com.example.mazaady.presentation.navigation
sealed class Route(val route: String) {
    object LaunchesList : Route("launches_list")
    object Booking : Route("book_launches")
    object LaunchDetail : Route("launch_detail/{launchId}") {
        fun createRoute(launchId: String) = "launch_detail/$launchId"
    }
}