package com.quibbly.kontent.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun MainContent() {
    val navController = rememberNavController()

    KontentApp {
        NavGraph(
            navController = navController,
            modifier = Modifier,
        )
    }

}