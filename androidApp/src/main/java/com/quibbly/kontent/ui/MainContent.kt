package com.quibbly.kontent.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.quibbly.common.search.DashboardStateStore

@Composable
fun MainContent(
    dashboardStateStore: DashboardStateStore,
) {
    val navController = rememberNavController()

    KontentApp {
        NavGraph(
            dashboardStateStore = dashboardStateStore,
            navController = navController,
            modifier = Modifier,
        )
    }

}