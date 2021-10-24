package com.quibbly.kontent.ui.composables

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.HourglassEmpty
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.quibbly.kontent.R

@Composable
fun EmptyPlaceHolder(
    modifier: Modifier = Modifier,
    hasError: Boolean,
    retry: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.disabled,
        ) {
            Icon(
                modifier = Modifier.size(75.dp),
                imageVector = Icons.Rounded.HourglassEmpty,
                contentDescription = stringResource(id = R.string.content_empty),
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = if (hasError) {
                    stringResource(id = R.string.content_fetch_failed)
                } else {
                    stringResource(id = R.string.content_empty)
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6,
            )
            Crossfade(targetState = hasError) {
                if (it) {
                    TextButton(onClick = retry) {
                        Text(stringResource(id = R.string.try_again))
                    }
                } else {
                    Text(
                        text = stringResource(id = R.string.content_not_done),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.caption,
                    )
                }
            }
        }
    }
}