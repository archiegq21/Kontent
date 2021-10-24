package com.quibbly.common.domain

import com.quibbly.common.db.KontentLocalSource
import com.quibbly.common.domain.search.Content
import com.quibbly.common.domain.search.MediaType
import kotlinx.coroutines.flow.*

interface DashboardRepository {
    fun getDashboardContent(): Flow<Result<List<Content>>>
    fun getContent(id: Long): Flow<Content?>
}

class DashboardRepositoryImpl(
    private val service: ITunesService,
    private val kontentLocalSource: KontentLocalSource,
) : DashboardRepository {

    override fun getDashboardContent(): Flow<Result<List<Content>>> =
        kontentLocalSource.getStoredContent()
            .map {
                Result.success(it)
            }.onStart {
                fetchContent().fold({
                    kontentLocalSource.storeContent(it)
                }, {
                    print(it.stackTraceToString())
                })
            }

    private suspend fun fetchContent(): Result<List<Content>> = service.search(
        term = "star",
        country = "au",
        media = MediaType.movie,
    ).mapCatching {
        it.results
    }

    override fun getContent(id: Long) = kontentLocalSource.getContentById(id)
}