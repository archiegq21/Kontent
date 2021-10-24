package com.quibbly.common.db

import com.quibbly.common.domain.search.Content
import kotlinx.coroutines.flow.*

interface KontentLocalSource {
    fun getStoredContent(): Flow<List<Content>>

    fun getContentById(id: Long): Flow<Content?>

    suspend fun storeContent(contents: List<Content>)
}

class MockKontentLocalSourceImpl : KontentLocalSource {

    private val contentStateFlow = MutableStateFlow<List<Content>>(emptyList())

    override fun getStoredContent(): Flow<List<Content>> = contentStateFlow

    override suspend fun storeContent(contents: List<Content>) {
        contentStateFlow.emit(contents)
    }

    override fun getContentById(id: Long): Flow<Content?> = flowOf(
        contentStateFlow.value.find { it.trackId == id }
    )
}