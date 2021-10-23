package com.quibbly.kontent.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.quibbly.kontent.ui.dashboard.DashboardDestinations
import com.quibbly.kontent.ui.dashboard.dashboardNavigation
import com.quibbly.kontent.ui.detail.detailViewNavigation

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = DashboardDestinations.DASHBOARD_ROUTE,
    ) {
        dashboardNavigation(
            navController = navController,
            modifier = modifier,
        )
        detailViewNavigation(
            navController = navController,
            modifier = modifier,
        )
    }
}

