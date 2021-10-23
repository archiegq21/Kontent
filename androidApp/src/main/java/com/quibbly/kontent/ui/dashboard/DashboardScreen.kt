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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import com.quibbly.kontent.R
import com.quibbly.kontent.ui.detail.DetailViewDestinations
import com.quibbly.kontent.ui.list.ContentList
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun DashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier,

) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DashboardTopBar(
                lastVisitedDate = LocalDateTime.parse("2018-12-14T09:55:00"),
                modifier = Modifier,
            )
        },
    ) { padding ->
        ContentList(
        contents = emptyList(),
            onContentSelected = {
                navController.navigate(DetailViewDestinations.DETAILS_ROUTE)
            },
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

@Composable
private fun DashboardTopBar(
    lastVisitedDate: LocalDateTime,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .wrapContentHeight()
            .statusBarsPadding(),
        elevation = 4.dp,
    ) {
        UserHeader(
            header = stringResource(R.string.app_name),
            lastVisited = lastVisitedDate,
            modifier = Modifier.fillMaxWidth()
                .navigationBarsPadding(bottom = false),
        )
    }
}

@Composable
fun UserHeader(
    header: String,
    lastVisited: LocalDateTime,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = header,
                style = MaterialTheme.typography.h6,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(
                    R.string.last_visited_date,
                    lastVisited.toJavaLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy"))
                ),
                style = MaterialTheme.typography.caption,
            )
        }
        Icon(
            imageVector = Icons.Rounded.Person,
            tint = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
            contentDescription = stringResource(R.string.your_avatar),
            modifier = Modifier
                .size(52.dp)
                .background(Color.LightGray, CircleShape)
                .padding(4.dp)
        )
    }
}