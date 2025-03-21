package com.example.truckercore

import android.app.Application
import com.example.truckercore.business_admin.di.businessAdminModule
import com.example.truckercore.model.configs.di.koinModules
import com.example.truckercore.view.enums.Flavor
import com.example.truckercore.view.expressions.getFlavor
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
        return when (getFlavor()) {
            Flavor.INDIVIDUAL -> emptyList()
            Flavor.BUSINESS_ADMIN -> koinModules.plus(businessAdminModule)
            Flavor.BUSINESS_DRIVER -> emptyList()
            else -> emptyList()
        }
    }

}