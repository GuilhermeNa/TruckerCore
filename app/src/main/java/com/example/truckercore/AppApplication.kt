package com.example.truckercore

import android.app.Application
import com.example.truckercore.model.configs.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin{
            androidContext(this@AppApplication)
            modules(koinModules)
        }
    }

}