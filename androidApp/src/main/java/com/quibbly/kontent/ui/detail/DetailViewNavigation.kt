package com.quibbly.kontent.ui.detail

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.quibbly.common.domain.Content
import com.quibbly.common.search.DashboardStateStore
import com.quibbly.kontent.ui.detail.DetailViewDestinations.Id

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.detailViewNavigation(
    dashboardStateStore: DashboardStateStore,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable(
        route = DetailViewDestinations.DETAILS_ROUTE,
        arguments = listOf(navArgument(Id) { type = NavType.LongType }),
    ) { backStackEntry ->
        val selectedId = backStackEntry.arguments!!.getLong(Id)
        val selectedContent by dashboardStateStore.getSelectedContentUi(selectedId)
            .collectAsState(Content.Empty)
        ContentDetailScreen(
            content = selectedContent,
            navController = navController,
            modifier = modifier,
        )
    }
}

object DetailViewDestinations {
    const val Id = "id"

    const val DETAILS_ROUTE = "details/{$Id}"

    fun navigationRouteFor(id: Long) = "details/$id"

}