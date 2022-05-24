package com.football.core.injection

import com.football.core.Repository
import com.football.core.RepositoryImpl
import org.koin.dsl.module

val coreModule = module {
    single<Repository> {
        RepositoryImpl(apiHelper = get())
    }
}