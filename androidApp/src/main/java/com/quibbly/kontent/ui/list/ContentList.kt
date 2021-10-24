package com.quibbly.kontent.ui.list

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.quibbly.common.domain.Content
import com.quibbly.common.search.ContentsState
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
    contentsState: ContentsState,
    onContentSelected: (Content) -> Unit,
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
            isRefreshing && contentsState.contents.isEmpty() -> emptyContentLoading()
            contentsState.contents.isEmpty() && contentsState.containsError() ->
                emptyContent(
                    hasError = true,
                    onRetry = onRetry,
                )
            contentsState.contents.isEmpty() ->
                emptyContent(
                    hasError = false,
                    onRetry = onRetry,
                )
            else -> content(contentsState, onContentSelected)
        }
    }
}

private fun LazyListScope.content(
    contentsState: ContentsState,
    onContentSelected: (Content) -> Unit,
) {
    item(key = ErrorBanner) {
        AnimatedVisibility(
            visible = contentsState.containsError(),
            enter = slideInVertically(),
            exit = slideOutVertically(),
        ) {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.caption,
                LocalContentColor provides contentColorFor(MaterialTheme.colors.error)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.error),
                    text = stringResource(R.string.content_fetch_failed),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
    item(key = MovieTitleKey) {
        Text(
            text = stringResource(R.string.movies),
            style = MaterialTheme.typography.h6,
        )
    }
    contentsState.contents.forEach { contentUi ->
        item {
            ContentCard(
                content = contentUi,
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