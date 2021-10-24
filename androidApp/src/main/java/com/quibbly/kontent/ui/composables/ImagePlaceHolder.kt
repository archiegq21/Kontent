package com.quibbly.kontent.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ImagePlaceHolder(
    modifier: Modifier = Modifier,
    image: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color.LightGray),
        contentAlignment = Alignment.Center,
    ) {
        // Since I didn't want to import a drawable just
        // to set a default placeholder for Coil, I simply
        // added this icon behind the Loading Image to act
        // as a Default image once, the image fetch failed
        Icon(
            imageVector = Icons.Rounded.Movie,
            tint = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        image()
    }
}