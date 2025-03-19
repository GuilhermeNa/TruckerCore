package com.example.truckercore

import android.app.Application
import com.example.truckercore.business_admin.di.businessAdminModule
import com.example.truckercore.model.configs.di.koinModules
import com.example.truckercore.view.enums.Flavor
import firebase.com.protolitewrapper.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.plus

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@AppApplication)
            modules(getCommonModules().plus(businessAdminModule))
        }
    }

    private fun getKoinModules(): List<Module> {
        return when (BuildConfig.FLAVOR) {
            Flavor.INDIVIDUAL.getName() -> emptyList()
            Flavor.BUSINESS_ADMIN.getName() -> getCommonModules().plus(businessAdminModule)
            Flavor.BUSINESS_DRIVER.getName() -> emptyList()
            else -> emptyList()
        }
    }

    private fun getCommonModules(): List<Module> = koinModules

}