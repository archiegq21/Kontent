package com.quibbly.kontent.ui.detail

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal fun NavGraphBuilder.detailViewNavigation(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable(DetailViewDestinations.DETAILS_ROUTE) {
        ContentDetailScreen(
            navController = navController,
            modifier = modifier,
        )
    }
}

object DetailViewDestinations {
    const val DETAILS_ROUTE = "/details"
}