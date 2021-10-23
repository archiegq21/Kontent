package com.quibbly.kontent.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DriveFileRenameOutline
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.insets.navigationBarsHeight
import com.quibbly.common.domain.search.Content
import com.quibbly.kontent.R

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ContentList(
    contents: List<Content>,
    onContentSelected: () -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
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
        item {
            Text(
                text = stringResource(R.string.movies),
                style = MaterialTheme.typography.h6,
            )
        }
//        contents.forEach { repo ->
        items(count = 100) {
            ContentCard(
                onContentSelected = onContentSelected,
                modifier = Modifier.fillMaxSize(),
            )
//            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentCard(
    onContentSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onContentSelected,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Movie,
                    tint = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = rememberImagePainter(
                        data = "https://www.example.com/image.jpg",
                        builder = {
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = null,
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "Bad Boys for Life",
                    style = MaterialTheme.typography.subtitle1,
                )
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.medium,
                    LocalTextStyle provides MaterialTheme.typography.caption,
                ) {
                    Text(
                        modifier = Modifier.border(
                            1.dp,
                            LocalContentColor.current,
                            CircleShape,
                        ).padding(
                            horizontal = 8.dp,
                            vertical = 4.dp,
                        ),
                        text = "Genre"
                    )
                }
                Text(
                    text = "$1,000.00",
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}