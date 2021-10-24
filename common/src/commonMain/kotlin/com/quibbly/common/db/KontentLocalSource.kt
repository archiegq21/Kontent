package com.quibbly.common.db

import com.quibbly.common.dto.search.ContentDto
import com.quibbly.common.dto.search.Kind
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.*

interface KontentLocalSource {
    fun getStoredContent(): Flow<List<ContentDto>>

    fun getContentById(id: Long): Flow<ContentDto?>

    suspend fun storeContent(contentDtos: List<ContentDto>)
}

class KontentLocalSourceImpl(
    private val database: KontentDatabase,
) : KontentLocalSource {

    override fun getStoredContent(): Flow<List<ContentDto>> =
        database.kontentQueries.selectAllContent()
            .asFlow()
            .mapToList()
            .map { dbDto -> dbDto.map { it.toContentDto() } }

    override suspend fun storeContent(contentDtos: List<ContentDto>) {
        database.kontentQueries.transaction {
            database.kontentQueries.deleteAllContent()
            contentDtos.forEach { contentDto ->
                database.kontentQueries.insertContent(
                    id = null,
                    trackId = contentDto.trackId,
                    wrapperType = contentDto.wrapperType,
                    kind = contentDto.kind,
                    artistName = contentDto.artistName,
                    trackName = contentDto.trackName,
                    trackViewUrl = contentDto.trackViewUrl,
                    previewUrl = contentDto.previewUrl,
                    artworkUrl30 = contentDto.artworkUrl30,
                    artworkUrl60 = contentDto.artworkUrl60,
                    artworkUrl100 = contentDto.artworkUrl100,
                    collectionPrice = contentDto.collectionPrice,
                    trackPrice = contentDto.trackPrice,
                    collectionHdPrice = contentDto.collectionHdPrice,
                    trackHdPrice = contentDto.trackHdPrice,
                    country = contentDto.country,
                    currency = contentDto.currency,
                    primaryGenreName = contentDto.primaryGenreName,
                    contentAdvisoryRating = contentDto.contentAdvisoryRating,
                    description = contentDto.description,
                    shortDescription = contentDto.shortDescription,
                    longDescription = contentDto.longDescription,
                )
            }
        }
    }

    override fun getContentById(id: Long): Flow<ContentDto?> =
        database.kontentQueries.getContentWithId(id)
            .asFlow()
            .mapToOneOrNull()
            .map { it?.toContentDto() }

    private fun ContentDbDto.toContentDto() = ContentDto(
        wrapperType = wrapperType,
        kind = kind ?: Kind.featureMovie,
        trackId = trackId,
        artistName = artistName,
        trackName = trackName,
        trackViewUrl = trackViewUrl,
        previewUrl = previewUrl,
        artworkUrl30 = artworkUrl30,
        artworkUrl60 = artworkUrl60,
        artworkUrl100 = artworkUrl100,
        collectionPrice = collectionPrice,
        trackPrice = trackPrice,
        collectionHdPrice = collectionHdPrice,
        trackHdPrice = trackHdPrice,
        country = country,
        currency = currency,
        primaryGenreName = primaryGenreName,
        contentAdvisoryRating = contentAdvisoryRating,
        description = description,
        shortDescription = shortDescription,
        longDescription = longDescription,
    )
}