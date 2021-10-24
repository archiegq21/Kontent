package com.quibbly.common.domain.search

import com.quibbly.common.search.ContentUi
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val resultCount: Int,
    val results: List<Content>,
)

@Serializable
data class Content(
    val wrapperType: String,
    val kind: Kind,
    val collectionId: Long? = null,
    val trackId: Long,
    val artistName: String,
    val collectionName: String? = null,
    val trackName: String,
    val collectionCensoredName: String? = null,
    val trackCensoredName: String? = null,
    val collectionArtistId: String? = null,
    val collectionArtistViewUrl: String? = null,
    val collectionViewUrl: String? = null,
    val trackViewUrl: String,
    val previewUrl: String,
    val artworkUrl30: String? = null,
    val artworkUrl60: String? = null,
    val artworkUrl100: String? = null,
    val collectionPrice: Double,
    val trackPrice: Double,
    val trackRentalPrice: Double? = null,
    val collectionHdPrice: Double,
    val trackHdPrice: Double,
    val trackHdRentalPrice: Double? = null,
    val releaseDate: Instant? = null,
    val collectionExplicitness: String,
    val trackExplicitness: String?,
    val discCount: Double? = null,
    val discNumber: Int? = null,
    val trackCount: Int? = null,
    val trackNumber: Int? = null,
    val trackTimeMillis: Long? = null,
    val artistId: Long? = null,
    val artistViewUrl: String? = null,
    val copyright: String? = null,
    val country: String,
    val currency: String,
    val primaryGenreName: String,
    val description: String? = null,
    val contentAdvisoryRating: String,
    val shortDescription: String? = null,
    val longDescription: String? = null,
    val hasITunesExtras: Boolean? = null,
)

fun Content.toContentUi() = ContentUi(
    id = trackId,
    title = trackName,
    artworkUrl = artworkUrl100 ?: "",
    genre = primaryGenreName,
    price = trackPrice,
    currency = currency,
    country = country,
    description = when {
        longDescription != null -> longDescription
        shortDescription != null -> shortDescription
        description != null -> description
        else -> ""
   },
    artist = artistName,
)