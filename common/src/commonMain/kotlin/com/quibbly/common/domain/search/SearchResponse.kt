package com.quibbly.common.domain.search

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
    val collectionId: Long,
    val trackId: Long,
    val artistName: String,
    val collectionName: String,
    val trackName: String,
    val collectionCensoredName: String,
    val trackCensoredName: String? = null,
    val collectionArtistId: String,
    val collectionArtistViewUrl: String,
    val collectionViewUrl: String,
    val trackViewUrl: String,
    val previewUrl: String,
    val artworkUrl30: String? = null,
    val artworkUrl60: String? = null,
    val artworkUrl100: String? = null,
    val collectionPrice: Double,
    val trackPrice: Double,
    val trackRentalPrice: Double,
    val collectionHdPrice: Double,
    val trackHdPrice: Double,
    val trackHdRentalPrice: Int?,
    val releaseDate: Instant? = null,
    val collectionExplicitness: Int?,
    val trackExplicitness: Int?,
    val discCount: Double? = null,
    val discNumber: Int,
    val trackCount: Int,
    val trackNumber: Int,
    val trackTimeMillis: Long? = null,
    val artistId: Long? = null,
    val artistViewUrl: String? = null,
    val copyright: String,
    val country: String,
    val currency: String,
    val primaryGenreName: String,
    val description: String,
    val contentAdvisoryRating: String,
    val shortDescription: String? = null,
    val longDescription: String? = null,
    val hasITunesExtras: Boolean,
)