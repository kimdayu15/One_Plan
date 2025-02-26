package com.gems.one_plan.navigation

sealed class NavItem( val route: String) {
    object OnePlanScreen : NavItem("one_plan")
}