package com.quibbly.common.network

import io.ktor.client.*

inline fun <reified T> HttpClient.doRequest(
    request: HttpClient.() -> T
): Result<T> = try {
    Result.success(request())
} catch (e: Throwable) {
    Result.failure(NetworkError)
}

// TODO create other specific Network Error
object NetworkError : Throwable()