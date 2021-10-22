package com.quibbly.common.network

import io.ktor.client.engine.*
import io.ktor.client.engine.ios.*

internal actual fun platformClientEngine(): HttpClientEngine = Ios.create {
    // TODO
}