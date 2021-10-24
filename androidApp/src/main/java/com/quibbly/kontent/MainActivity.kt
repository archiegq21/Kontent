package com.quibbly.kontent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.quibbly.kontent.ui.AppViewModel
import com.quibbly.kontent.ui.MainContent

class MainActivity : ComponentActivity() {

    private val appViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MainContent(
                dashboardStateStore = appViewModel.dashboardStore,
            )
        }
    }
}