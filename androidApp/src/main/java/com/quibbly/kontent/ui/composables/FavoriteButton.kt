package com.quibbly.kontent.ui.composables

import androidx.compose.animation.Crossfade
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FavoriteIconButton(
    favoriteState: FavoriteState,
    onFavoriteChanged: (FavoriteState) -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        modifier = modifier,
        onClick = { onFavoriteChanged(favoriteState.toggle()) }
    ) {
        Crossfade(targetState = favoriteState) {
            when (it) {
                FavoriteState.NotFavorite -> Icon(
                    imageVector = Icons.Rounded.FavoriteBorder,
                    contentDescription = "Favorite"
                )
                FavoriteState.Favorite -> Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = "Favorite On"
                )
            }
        }
    }
}

enum class FavoriteState {
    NotFavorite, Favorite;

    fun toggle(): FavoriteState = when (this) {
        NotFavorite -> Favorite
        Favorite -> NotFavorite
    }
}