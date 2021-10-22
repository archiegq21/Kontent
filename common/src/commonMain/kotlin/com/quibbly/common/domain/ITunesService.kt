package com.quibbly.common.domain

import com.quibbly.common.domain.search.*
import io.ktor.client.*
import io.ktor.client.request.*

interface ITunesService {
    suspend fun search(
        term: String,
        country: String,
        media: MediaType = MediaType.all,
        entity: String? = null,
        attribute: String? = null,
        limit: Int = 50,
        lang: Language = Language.en_us,
        version: Version = Version.v2,
        explicit: Explicit = Explicit.Yes,
    ): Result<SearchResponse>
}

class ITunesServiceImpl(
    private val httpClient: HttpClient,
) : ITunesService {

    override suspend fun search(
        term: String,
        country: String,
        media: MediaType,
        entity: String?,
        attribute: String?,
        limit: Int,
        lang: Language,
        version: Version,
        explicit: Explicit,
    ) = httpClient.runCatching {
        get<SearchResponse>("/search") {
            parameter("term", term)
            parameter("country", country)
            parameter("media", media)
            entity?.let { parameter("entity", it) }
            attribute?.let { parameter("attribute", it) }
            parameter("limit", limit)
            parameter("lang", lang)
            parameter("version", version)
            parameter("explicit", explicit)
        }
    }

}