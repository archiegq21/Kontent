package com.quibbly.common.di

import com.quibbly.common.domain.ITunesService
import com.quibbly.common.domain.ITunesServiceImpl
import org.koin.dsl.module

fun itunesModule() = module {

    single<ITunesService> {
        ITunesServiceImpl(get())
    }

}