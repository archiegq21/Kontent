package com.quibbly.kontent.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier

@Composable
fun DetailValue(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
    value: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium,
            LocalTextStyle provides MaterialTheme.typography.caption,
            content = label,
        )
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.high,
            LocalTextStyle provides MaterialTheme.typography.subtitle1,
            content = value,
        )
    }
}