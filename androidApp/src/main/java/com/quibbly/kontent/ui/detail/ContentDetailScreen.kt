package com.quibbly.kontent.ui.detail

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.quibbly.common.search.ContentUi
import com.quibbly.kontent.R
import com.quibbly.kontent.ui.composables.DetailValue
import com.quibbly.kontent.ui.composables.FavoriteIconButton
import com.quibbly.kontent.ui.composables.FavoriteState
import com.quibbly.kontent.ui.util.formatToAmount

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ContentDetailScreen(
    contentUi: ContentUi?,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DetailTopBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(bottom = false),
                title = {
                    Text(
                        modifier = Modifier,
                        text = contentUi?.title ?: "",
                        style = MaterialTheme.typography.h6,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { backDispatcher?.onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            modifier = Modifier
                        )
                    }
                },
                actions = {
                    FavoriteIconButton(
                        favoriteState = FavoriteState.NotFavorite,
                        onFavoriteChanged = {},
                        modifier = Modifier,
                    )
                },
            )
        },
        bottomBar = {
            DetailBottomBar(
                contentUi = contentUi,
                modifier = Modifier,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .padding(padding)
                .navigationBarsPadding(bottom = false),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = rememberImagePainter(
                    data = contentUi?.artworkUrl ?: "",
                ),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DetailValue(
                    modifier = Modifier.weight(2f),
                    label = { Text(text = stringResource(R.string.artist)) },
                    value = {
                        Text(
                            text = contentUi?.artist ?: "",
                            maxLines = 1,
                        )
                    }
                )
                DetailValue(
                    modifier = Modifier.weight(1f),
                    label = { Text(text = stringResource(R.string.genre)) },
                    value = { Text(text = contentUi?.genre ?: "") }
                )
            }
            Spacer(Modifier.height(24.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start),
                text = stringResource(R.string.description),
                style = MaterialTheme.typography.h6,
            )
            Spacer(Modifier.height(16.dp))
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.medium,
                LocalTextStyle provides MaterialTheme.typography.subtitle1,
            ) {
                Text(
                    text = contentUi?.description ?: "",
                    textAlign = TextAlign.Justify,
                )
            }
        }
    }

}

@Composable
fun DetailTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 4.dp,
) {
    Surface(
        color = backgroundColor,
        elevation = elevation,
        modifier = Modifier,
    ) {
        Column(
            modifier = Modifier,
        ) {
            TopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                backgroundColor = Color.Transparent,
                contentColor = contentColor,
                elevation = 0.dp,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun DetailBottomBar(
    contentUi: ContentUi?,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 8.dp,
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DetailValue(
                modifier = Modifier.weight(1f),
                label = { Text(text = stringResource(R.string.price)) },
                value = {
                    Text(text = remember(contentUi?.price, contentUi?.currency) {
                        contentUi?.let {
                            contentUi.price.formatToAmount(contentUi.currency)
                        } ?: ""
                    })
                }
            )
            Button(
                modifier = Modifier,
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                ),
                onClick = {
                      // TODO, This does not do anything
                      //  was only added for additional elements
                },
            ) {
                Text(text = stringResource(id = R.string.purchase))
            }
        }
    }
}