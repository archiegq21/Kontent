package com.quibbly.common.di

import com.quibbly.common.services.DashboardRepository
import com.quibbly.common.services.DashboardRepositoryImpl
import org.koin.dsl.module

fun dashboardModule() = module {

    single<DashboardRepository> {
        DashboardRepositoryImpl(get(), get())
    }

}