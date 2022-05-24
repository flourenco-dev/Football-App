package com.football.ui.injection

import com.football.ui.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        NewsViewModel(repository = get())
    }
}