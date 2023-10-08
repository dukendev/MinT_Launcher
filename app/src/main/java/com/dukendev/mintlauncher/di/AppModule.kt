package com.dukendev.mintlauncher.di

import com.dukendev.mintlauncher.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel()
    }
}