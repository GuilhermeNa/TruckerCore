package com.example.truckercore.business_driver

import android.app.Application
import com.example.truckercore.model.configs.build.FlavorProvider
import com.example.truckercore.model.configs.di.koinModules
import com.example.truckercore.view_model.di.commonViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.plus

class DriverApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DriverApplication)
            modules(getKoinModules())
        }
    }

    private fun getKoinModules(): List<Module> = koinModules.plus(commonViewModelModule)

}