package com.quibbly.kontent

import android.app.Application
import com.quibbly.common.db.AndroidDatabaseDriver
import com.quibbly.common.di.initKoin
import org.koin.core.Koin
import org.koin.dsl.module

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
    }

}