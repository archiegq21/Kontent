package com.quibbly.common.search

import com.quibbly.common.db.KontentPreferences
import com.quibbly.common.domain.Content
import com.quibbly.common.services.DashboardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DashboardStateStore(
    private val scope: CoroutineScope,
    initialState: ContentsState = ContentsState(),
) : KoinComponent {

    private val dashboardRepository: DashboardRepository by inject()
    private val kontentPreferences: KontentPreferences by inject()

    val lastVisitedDate = MutableStateFlow(
        kontentPreferences.lastVisitedDate
    ).asStateFlow()

    private val isRefreshingFlow = MutableStateFlow(false)
    val isRefreshing = isRefreshingFlow.asStateFlow()
    fun refresh() {
        scope.launch {
            isRefreshingFlow.emit(true)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val contentsState: StateFlow<ContentsState> = isRefreshingFlow.filter { it }
        .flatMapLatest {
            dashboardRepository.getDashboardContent()
        }.scan(initialState) { previous, current ->
            current.fold({ contents ->
                ContentsState(contents = contents)
            }, {
                previous.copy(error = it)
            })
        }.onEach {
            isRefreshingFlow.emit(false)
        }.stateIn(scope, SharingStarted.Eagerly, initialState)

    init {
        refresh()
    }

    fun getSelectedContentUi(id: Long): Flow<Content> =
        dashboardRepository.getContent(id)
            .map { it ?: Content.Empty }
}

data class ContentsState(
    val contents: List<Content> = emptyList(),
    val error: Throwable? = null,
) {
    fun containsError() = error != null
}