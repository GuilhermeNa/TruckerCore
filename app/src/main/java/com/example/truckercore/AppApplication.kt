package com.example.truckercore

import android.app.Application
import com.example.truckercore.business_admin.di.businessAdminModule
import com.example.truckercore.model.configs.di.koinModules
import com.example.truckercore.view.enums.Flavor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.plus

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(getKoinModules())
        }
    }

    private fun getKoinModules(): List<Module> {
        return when (getAppLabel()) {
            Flavor.INDIVIDUAL.getName() -> emptyList()
            Flavor.BUSINESS_ADMIN.getName() -> koinModules.plus(businessAdminModule)
            Flavor.BUSINESS_DRIVER.getName() -> emptyList()
            else -> emptyList()
        }
    }

    private fun getAppLabel() =
        applicationContext.packageManager.getApplicationLabel(applicationInfo)

}