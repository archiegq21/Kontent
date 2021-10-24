package com.quibbly.kontent.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.quibbly.common.domain.Content
import com.quibbly.common.search.ContentsState
import com.quibbly.kontent.R
import com.quibbly.kontent.ui.list.ContentList
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun DashboardScreen(
    lastVisitedDate: LocalDateTime?,
    contentsState: ContentsState,
    onContentUiSelected: (Content) -> Unit,
    swipeRefreshState: SwipeRefreshState,
    onRefresh: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DashboardTopBar(
                lastVisitedDate = lastVisitedDate,
                modifier = Modifier,
            )
        },
    ) { padding ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = onRefresh,
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = false,
                    backgroundColor = MaterialTheme.colors.primary,
                    shape = MaterialTheme.shapes.small,
                )
            },
        ) {
            ContentList(
                isRefreshing = swipeRefreshState.isRefreshing,
                onRetry = onRefresh,
                contentsState = contentsState,
                onContentSelected = onContentUiSelected,
                modifier = Modifier.fillMaxSize(),
                contentPadding = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.navigationBars,
                    additionalTop = 16.dp + padding.calculateTopPadding(),
                    additionalEnd = 16.dp + padding.calculateEndPadding(LocalLayoutDirection.current),
                    additionalBottom = 16.dp + padding.calculateBottomPadding(),
                    additionalStart = 16.dp + padding.calculateStartPadding(LocalLayoutDirection.current),
                )
            )
        }
    }
}

@Composable
private fun DashboardTopBar(
    lastVisitedDate: LocalDateTime?,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .wrapContentHeight()
            .statusBarsPadding(),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(bottom = false)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.h6,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.last_visited_date),
                style = MaterialTheme.typography.caption,
            )
            Text(
                text = lastVisitedDate?.toJavaLocalDateTime()
                    ?.format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy hh:mm a"))
                    ?: stringResource(id = R.string.first_visit),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.caption,
            )
        }
    }
}