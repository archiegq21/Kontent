package com.quibbly.common.dto.search

import com.quibbly.common.domain.Content
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val resultCount: Int,
    val results: List<ContentDto>,
)