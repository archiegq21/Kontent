package com.quibbly.common.search

import com.quibbly.common.domain.DashboardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DashboardStore(
    scope: CoroutineScope,
): Store(scope), KoinComponent {

    private val dashboardRepository: DashboardRepository by inject()

    val dashboardContent = dashboardRepository.getDashboardContent()
        .stateIn(scope, SharingStarted.Eagerly, emptyList())


}