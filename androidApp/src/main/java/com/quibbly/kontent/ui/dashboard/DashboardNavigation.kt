package com.quibbly.kontent.ui.dashboard

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal fun NavGraphBuilder.dashboardNavigation(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable(DashboardDestinations.DASHBOARD_ROUTE) {
        val viewModel = viewModel<DashboardViewModel>()

        DashboardScreen(
            navController = navController,
            modifier = modifier,
        )
    }
}

object DashboardDestinations {
    const val DASHBOARD_ROUTE = "/dashboard"
}