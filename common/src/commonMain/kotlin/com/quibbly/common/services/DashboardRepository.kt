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

    /**
     * Retrieves the list of [Content] and since, this function
     * returns a flow, it will automatically emit new list
     * everytime the state changes
     *
     * @returns [Result] of the list of [Content]
     * */
    fun getDashboardContent(): Flow<Result<List<Content>>>

    /**
     * Retrieves a Content by the given [id].
     *
     * @param id is the trackId of the Content
     *
     * @returns [Content] but would return null when no
     *  content is retrieved
     * */
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