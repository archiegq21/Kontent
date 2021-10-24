package com.quibbly.common.di

import com.quibbly.common.db.DatabaseDriverFactory
import com.quibbly.common.db.IosDatabaseDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.dsl.module

fun iosKoin() = initKoin(
    platformModule = module {
        single {
            IosDatabaseDriver()
        }
    }
)