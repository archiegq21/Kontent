package com.quibbly.common.di

import com.quibbly.common.db.*
import com.russhwolf.settings.Settings
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

    single {
        Settings()
    }

    single<KontentLocalSource> {
        KontentLocalSourceImpl(get())
    }

    single<KontentPreferences> {
        KontentPreferencesImpl(get())
    }
}