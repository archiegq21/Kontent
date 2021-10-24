package com.quibbly.kontent.ui.dashboard

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.quibbly.common.search.DashboardStore
import com.quibbly.kontent.ui.detail.DetailViewDestinations

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.dashboardNavigation(
    dashboardStore: DashboardStore,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable(DashboardDestinations.DASHBOARD_ROUTE) {
        val isRefreshing by dashboardStore.isRefreshing.collectAsState()
        val contentUiState by dashboardStore.dashboardContent.collectAsState()
        DashboardScreen(
            contentUiState = contentUiState,
            onContentUiSelected = {
                navController.navigate(DetailViewDestinations.navigationRouteFor(it.id))
            },
            swipeRefreshState = rememberSwipeRefreshState(isRefreshing),
            onRefresh = dashboardStore::refresh,
            navController = navController,
            modifier = modifier,
        )
    }
}

object DashboardDestinations {
    const val DASHBOARD_ROUTE = "dashboard"
}