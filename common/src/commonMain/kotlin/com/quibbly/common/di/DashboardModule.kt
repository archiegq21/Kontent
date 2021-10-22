package com.quibbly.common.di

import com.quibbly.common.domain.DashboardRepository
import com.quibbly.common.domain.DashboardRepositoryImpl
import com.quibbly.common.domain.ITunesService
import com.quibbly.common.domain.ITunesServiceImpl
import org.koin.dsl.module

fun dashboardModule() = module {

    single<DashboardRepository> {
        DashboardRepositoryImpl(get(), get())
    }

}