package com.quibbly.common.di

import com.quibbly.common.db.*
import com.squareup.sqldelight.EnumColumnAdapter
import org.koin.dsl.module

fun localSourceModule() = module {

    single {
        KontentDatabase(
            driver = get(),
            ContentDbDtoAdapter = ContentDbDto.Adapter(
                kindAdapter = EnumColumnAdapter(),
            )
        )
    }

    single<KontentLocalSource> {
        KontentLocalSourceImpl(get())
    }

}