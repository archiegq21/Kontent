package com.quibbly.common.db

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

interface KontentPreferences {

    /**
     * Returns the [LocalDateTime] of the previous
     * run of the application or returns null when
     * nothings is stored yet (such as on first app run)
     * */
    val lastVisitedDate: LocalDateTime?

    /**
     * Sets the VisitedDate when the app starts up
     * */
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