package com.quibbly.common.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

internal actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(KontentDatabase.Schema, DB_NAME)
    }
}

fun IosDatabaseDriver() = DatabaseDriverFactory().createDriver()