package com.quibbly.common.search

data class ContentUi(
    val id: Long,
    val artworkUrl: String,
    val title: String,
    val genre: String,
    val price: Double,
    val currency: String,
    val country: String,
    val description: String,
    val artist: String,
) {
    companion object {
        val Empty = ContentUi(
            id = 0,
            artworkUrl = "",
            title = "",
            genre = "",
            price = 0.0,
            currency = "",
            country = "",
            description = "",
            artist = "",
        )
    }
}