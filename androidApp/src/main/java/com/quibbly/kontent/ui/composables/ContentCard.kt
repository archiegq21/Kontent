package com.quibbly.kontent.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.quibbly.common.search.ContentUi
import com.quibbly.kontent.ui.util.formatToAmount
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ContentCard(
    contentUi: ContentUi,
    onContentSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ContentCard(
        image = {
            ImagePlaceHolder(
                modifier = Modifier,
            ) {
                Image(
                    modifier = Modifier.requiredHeight(100.dp)
                        .aspectRatio(2/3f),
                    painter = rememberImagePainter(
                        data = contentUi.artworkUrl,
                    ),
                    contentDescription = null,
                )
            }
        },
        title = {
            Text(text = contentUi.title)
        },
        genre = {
            Text(text = contentUi.genre)
        },
        price = {
            Text(text = remember(contentUi.price, contentUi.currency) {
                contentUi.price.formatToAmount(contentUi.currency)
            })
        },
        onContentSelected = onContentSelected,
        modifier = modifier,
    )
}

@Composable
fun ContentCardPlaceHolder(
    modifier: Modifier = Modifier,
) {
    ContentCard(
        image = {},
        title = {},
        genre = {},
        price = {},
        onContentSelected = {},
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .shimmer(),
        enabled = false,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentCard(
    image: @Composable () -> Unit,
    title: @Composable () -> Unit,
    genre: @Composable BoxScope.() -> Unit,
    price: @Composable () -> Unit,
    onContentSelected: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Surface(
        enabled = enabled,
        onClick = onContentSelected,
        modifier = modifier.requiredHeight(100.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            image()
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                    LocalTextStyle provides MaterialTheme.typography.subtitle1,
                    content = title,
                )
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.medium,
                    LocalTextStyle provides MaterialTheme.typography.caption,
                ) {
                    Box(
                        modifier = Modifier
                            .border(
                                1.dp,
                                LocalContentColor.current,
                                CircleShape,
                            )
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp,
                            ),
                        content = genre,
                    )
                }
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                    LocalTextStyle provides MaterialTheme.typography.body1,
                    content = price,
                )
            }
        }
    }
}