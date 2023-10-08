package com.dukendev.mintlauncher

import android.app.Application
import com.dukendev.mintlauncher.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MintApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModule)
        }
    }

}