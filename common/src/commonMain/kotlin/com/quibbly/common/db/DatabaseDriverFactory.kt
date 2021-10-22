package com.quibbly.common.db

import com.squareup.sqldelight.db.SqlDriver

internal expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

const val DB_NAME = "content.db"