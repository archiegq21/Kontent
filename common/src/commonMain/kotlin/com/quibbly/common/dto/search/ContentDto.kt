package com.quibbly.common.dto.search

import com.quibbly.common.domain.Content
import kotlinx.serialization.Serializable

@Serializable
data class ContentDto(
    val wrapperType: String,
    val kind: Kind,
    val trackId: Long,
    val artistName: String,
    val trackName: String,
    val trackViewUrl: String,
    val previewUrl: String,
    val artworkUrl30: String? = null,
    val artworkUrl60: String? = null,
    val artworkUrl100: String? = null,
    val collectionPrice: Double,
    val trackPrice: Double,
    val collectionHdPrice: Double,
    val trackHdPrice: Double,
    val country: String,
    val currency: String,
    val primaryGenreName: String,
    val contentAdvisoryRating: String,
    val description: String? = null,
    val shortDescription: String? = null,
    val longDescription: String? = null,
) {

    fun asContent() = Content(
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

}