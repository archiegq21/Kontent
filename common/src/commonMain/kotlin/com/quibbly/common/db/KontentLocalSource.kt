package com.quibbly.common.db

import com.quibbly.common.domain.search.Content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

interface KontentLocalSource {
    fun getStoredContent(): Flow<List<Content>>

    suspend fun storeContent(contents: List<Content>)
}

class MockKontentLocalSourceImpl : KontentLocalSource {

    private val contentStateFlow = MutableSharedFlow<List<Content>>()

    override fun getStoredContent(): Flow<List<Content>> = contentStateFlow

    override suspend fun storeContent(contents: List<Content>) {
        contentStateFlow.emit(contents)
    }
}