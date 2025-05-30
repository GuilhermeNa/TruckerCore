package com.example.truckercore.business_admin

import android.app.Application
import com.example.truckercore.business_admin.config.di.adminModules
import com.example.truckercore.model.configs.di.modelModules
import com.example.truckercore.view_model.di.commonViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.plus

class AdminApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AdminApplication)
            modules(getKoinModules())
        }
    }

    private fun getKoinModules(): List<Module> =
        modelModules
            .plus(commonViewModelModule)
            .plus(adminModules)

}