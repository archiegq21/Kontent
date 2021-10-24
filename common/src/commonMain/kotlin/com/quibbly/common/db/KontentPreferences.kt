package com.quibbly.common.db

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

interface KontentPreferences {
    val lastVisitedDate: LocalDateTime?

    fun setLastVisitedDate(instant: Instant)
}

class KontentPreferencesImpl(
    private val settings: Settings,
): KontentPreferences {

    override var lastVisitedDate: LocalDateTime? = null
        private set

    init {
        lastVisitedDate = settings.getStringOrNull(LastVisitedDateKey)?.let {
            Instant.parse(it).toLocalDateTime(TimeZone.currentSystemDefault())
        }
    }

    override fun setLastVisitedDate(instant: Instant) {
        settings[LastVisitedDateKey] = instant.toString()
    }

    companion object {
        private const val LastVisitedDateKey = "LastVisitedDate"
    }
}