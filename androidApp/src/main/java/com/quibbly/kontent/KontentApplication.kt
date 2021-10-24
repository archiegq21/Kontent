package com.quibbly.kontent

import android.app.Application
import com.quibbly.common.db.AndroidDatabaseDriver
import com.quibbly.common.db.KontentPreferences
import com.quibbly.common.di.initKoin
import kotlinx.datetime.toKotlinInstant
import org.koin.core.Koin
import org.koin.dsl.module
import java.util.*

class KontentApplication : Application() {

    private lateinit var koin: Koin

    override fun onCreate() {
        super.onCreate()
        koin = initKoin(
            module {
                single {
                    AndroidDatabaseDriver(this@KontentApplication)
                }
            }
        ).koin

        // Set the Visited Time once the app started
        koin.get<KontentPreferences>().setLastVisitedDate(
            Calendar.getInstance().toInstant().toKotlinInstant()
        )
    }

}