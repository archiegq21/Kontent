package com.quibbly.kontent.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.quibbly.common.search.DashboardStore

@Composable
fun MainContent(
    dashboardStore: DashboardStore,
) {
    val navController = rememberNavController()

    KontentApp {
        NavGraph(
            dashboardStore = dashboardStore,
            navController = navController,
            modifier = Modifier,
        )
    }

}