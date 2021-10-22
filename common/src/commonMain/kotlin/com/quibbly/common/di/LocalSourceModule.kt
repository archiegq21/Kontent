package com.quibbly.common.di

import com.quibbly.common.db.KontentLocalSource
import com.quibbly.common.db.MockKontentLocalSourceImpl
import com.quibbly.common.domain.ITunesService
import com.quibbly.common.domain.ITunesServiceImpl
import org.koin.dsl.module

fun localSourceModule() = module {

    single<KontentLocalSource> {
        MockKontentLocalSourceImpl()
    }

}