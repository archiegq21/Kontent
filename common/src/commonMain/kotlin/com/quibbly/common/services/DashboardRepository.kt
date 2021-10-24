package com.quibbly.common.services

import com.quibbly.common.db.KontentLocalSource
import com.quibbly.common.domain.Content
import com.quibbly.common.dto.search.ContentDto
import com.quibbly.common.dto.search.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface DashboardRepository {
    fun getDashboardContent(): Flow<Result<List<Content>>>
    fun getContent(id: Long): Flow<Content?>
}

class DashboardRepositoryImpl(
    private val service: KontentService,
    private val kontentLocalSource: KontentLocalSource,
) : DashboardRepository {

    override fun getDashboardContent(): Flow<Result<List<Content>>> =
        kontentLocalSource.getStoredContent().map { contentDtos ->
            Result.success(
                contentDtos.map {
                    it.asContent()
                }
            )
        }.onStart {
            val list = fetchContent().getOrThrow()
            kontentLocalSource.storeContent(list)
        }.catch { e ->
            emit(Result.failure(e))
        }

    private suspend fun fetchContent(): Result<List<ContentDto>> = service.search(
        term = "star",
        country = "au",
        media = MediaType.movie,
    ).mapCatching {
        it.results
    }

    override fun getContent(id: Long) = kontentLocalSource.getContentById(id)
        .map { it?.asContent() }


}