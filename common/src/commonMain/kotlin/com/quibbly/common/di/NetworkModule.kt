package com.quibbly.common.di

import com.quibbly.common.network.platformClientEngine
import com.quibbly.common.network.HttpClient
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun networkModule() = module {

    single {
        Json {
            allowStructuredMapKeys = true
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    single {
        HttpClient(
            block = {
                url {
                    protocol = URLProtocol.HTTPS
                }
                host = "itunes.apple.com"
            },
            json = get(),
            engine = get(),
        )
    }

    single {
        platformClientEngine()
    }

}