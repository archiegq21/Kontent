package com.quibbly.common.network

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal actual fun platformClientEngine(): HttpClientEngine = OkHttp.create {
    OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .connectionPool(ConnectionPool(1, 5, TimeUnit.MINUTES))
        .dispatcher(Dispatcher())
        .build()
}