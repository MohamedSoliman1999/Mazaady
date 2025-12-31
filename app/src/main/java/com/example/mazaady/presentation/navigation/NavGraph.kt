package com.example.mazaady.presentation.navigation

import androidx.navigation.NavHostController

//class NavGraph {
//}
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mazaady.presentation.detailScreen.LaunchDetailScreen
import com.example.mazaady.presentation.listScreen.LaunchesListScreen

@Composable
fun LaunchesNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.LaunchesList.route,
        modifier = modifier
    ) {
        composable(route = Route.LaunchesList.route) {
            LaunchesListScreen(
                onLaunchClick = { launchId ->
                    navController.navigate(Route.LaunchDetail.createRoute(launchId))
                }
            )
        }

        composable(
            route = Route.LaunchDetail.route,
            arguments = listOf(
                navArgument("launchId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val launchId = backStackEntry.arguments?.getString("launchId") ?: ""
            LaunchDetailScreen(
                launchId = launchId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
