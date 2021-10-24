package com.quibbly.kontent.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.quibbly.common.search.ContentUi
import com.quibbly.common.search.ContentUiState
import com.quibbly.kontent.R
import com.quibbly.kontent.ui.composables.ContentCard
import com.quibbly.kontent.ui.composables.ContentCardPlaceHolder
import com.quibbly.kontent.ui.composables.EmptyPlaceHolder
import com.quibbly.kontent.ui.composables.shimmer

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ContentList(
    isRefreshing: Boolean,
    onRetry: () -> Unit,
    contentUiState: ContentUiState,
    onContentSelected: (ContentUi) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
    ) {
        when {
            isRefreshing && contentUiState.contents.isEmpty() -> emptyContentLoading()
            contentUiState.contents.isEmpty() && contentUiState.containsError() ->
                emptyContent(
                    hasError = true,
                    onRetry = onRetry,
                )
            contentUiState.contents.isEmpty() ->
                emptyContent(
                    hasError = false,
                    onRetry = onRetry,
                )
            else -> content(contentUiState, onContentSelected)
        }
    }
}

private fun LazyListScope.content(
    contentUiState: ContentUiState,
    onContentSelected: (ContentUi) -> Unit,
) {
    item(key = ErrorBanner) {
        AnimatedVisibility(
            visible = contentUiState.containsError(),
            enter = slideInVertically { -it },
            exit = slideOutVertically { it },
        ) {
            Text(
                text = stringResource(R.string.content_fetch_failed),
                style = MaterialTheme.typography.caption,
            )
        }
    }
    item(key = MovieTitleKey) {
        Text(
            text = stringResource(R.string.movies),
            style = MaterialTheme.typography.h6,
        )
    }
    contentUiState.contents.forEach { contentUi ->
        item {
            ContentCard(
                contentUi = contentUi,
                onContentSelected = { onContentSelected(contentUi) },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

private fun LazyListScope.emptyContentLoading() {
    item(key = MovieTitleKey) {
        Text(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .shimmer(),
            text = "",
        )
    }
    items(count = 100) {
        ContentCardPlaceHolder(modifier = Modifier.fillMaxSize())
    }
}

private fun LazyListScope.emptyContent(
    hasError: Boolean,
    onRetry: () -> Unit,
) {
    item(key = EmptyContent) {
        EmptyPlaceHolder(
            modifier = Modifier.fillParentMaxSize(),
            hasError = hasError,
            retry = onRetry,
        )
    }
}

private const val MovieTitleKey = "MovieTitle"
private const val ErrorBanner = "ErrorBanner"
private const val EmptyContent = "EmptyContent"