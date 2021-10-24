package com.quibbly.common.search

import com.quibbly.common.domain.DashboardRepository
import com.quibbly.common.domain.search.Content
import com.quibbly.common.domain.search.toContentUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DashboardStore(
    scope: CoroutineScope,
) : Store(scope), KoinComponent {

    private val dashboardRepository: DashboardRepository by inject()

    private val isRefreshingFlow = MutableStateFlow(false)
    val isRefreshing = isRefreshingFlow.asStateFlow()
    fun refresh() {
        scope.launch {
            isRefreshingFlow.emit(true)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val dashboardContent: StateFlow<ContentUiState> = isRefreshingFlow.filter { it }
        .flatMapLatest {
            dashboardRepository.getDashboardContent()
        }.scan(ContentUiState()) { previous, current ->
            current.fold({ contents ->
                ContentUiState(
                    contents = contents.map {
                        it.toContentUi()
                    }
                )
            }, {
                previous.copy(error = it)
            })
        }.onEach {
            isRefreshingFlow.emit(false)
        }.stateIn(scope, SharingStarted.Eagerly, ContentUiState())

    init {
        refresh()
    }

    fun getSelectedContentUi(id: Long): Flow<ContentUi?> =
        dashboardRepository.getContent(id).map { it?.toContentUi() }
}

data class ContentUiState(
    val contents: List<ContentUi> = emptyList(),
    val error: Throwable? = null,
) {
    fun containsError() = error != null
}