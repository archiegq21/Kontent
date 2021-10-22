package com.quibbly.common.db

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

internal actual class DatabaseDriverFactory(
    private val context: Context,
) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(KontentDatabase.Schema, context, DB_NAME)
    }
}

fun AndroidDatabaseDriver(
    context: Context
) = DatabaseDriverFactory(context).createDriver()