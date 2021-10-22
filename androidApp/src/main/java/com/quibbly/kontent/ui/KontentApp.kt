package com.quibbly.kontent.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.google.accompanist.insets.ProvideWindowInsets
import com.quibbly.kontent.ui.theme.KontentTheme

@Composable
fun KontentApp(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    ProvideWindowInsets {
        KontentTheme(
            darkTheme = darkTheme,
            content = content,
        )
    }
}