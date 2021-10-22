package com.quibbly.common.domain

import com.quibbly.common.db.KontentLocalSource
import com.quibbly.common.domain.search.Content
import com.quibbly.common.domain.search.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEmpty

interface DashboardRepository {
    fun getDashboardContent(): Flow<List<Content>>
}

class DashboardRepositoryImpl(
    private val service: ITunesService,
    private val kontentLocalSource: KontentLocalSource,
): DashboardRepository {

    override fun getDashboardContent(): Flow<List<Content>> = kontentLocalSource.getStoredContent()
        .onEmpty {
            val response = service.search(
                term = "star",
                country = "au",
                media = MediaType.movie,
            )

            response.fold({
                kontentLocalSource.storeContent(it.results)
            }, {
                println(it.stackTraceToString())
            })
        }

}