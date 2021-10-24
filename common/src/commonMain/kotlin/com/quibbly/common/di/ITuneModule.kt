package com.quibbly.common.di

import com.quibbly.common.services.KontentService
import com.quibbly.common.services.KontentServiceImpl
import org.koin.dsl.module

fun itunesModule() = module {

    single<KontentService> {
        KontentServiceImpl(get())
    }

}