package com.gems.one_plan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gems.one_plan.common.MyViewModel
import com.gems.one_plan.ui.screen.ItemScreen
import com.gems.one_plan.ui.screen.OnePlanScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: MyViewModel,
    startDestination: String = NavItem.OnePlanScreen.route
) {

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavItem.OnePlanScreen.route) {
            OnePlanScreen(
                viewModel,
                navController = navController
            )
        }

        composable("item/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").toString()
            ItemScreen(id, navController, viewModel)
        }
    }
}