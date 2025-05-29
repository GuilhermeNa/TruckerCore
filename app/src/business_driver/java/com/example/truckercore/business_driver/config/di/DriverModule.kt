package com.example.truckercore.business_driver.config.di

import androidx.navigation.NavController
import com.example.truckercore.business_driver.config.flavor.FlavorDriverStrategy
import com.example.truckercore.business_driver.view.fragments.splash_driver.navigator.SplashDriverNavigatorImpl
import com.example.truckercore.business_driver.view.fragments.splash_driver.navigator.SplashDriverNavigator
import com.example.truckercore.business_driver.view_model.view_models.splash_driver.SplashDriverViewModel
import com.example.truckercore.model.configs.flavor.contracts.FlavorStrategy
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val driverModules = module {
    single<FlavorStrategy> { FlavorDriverStrategy() }

    // Splash Driver Fragment
    viewModel<SplashDriverViewModel> { SplashDriverViewModel(get()) }
    factory<SplashDriverNavigator> { (param: NavController) -> SplashDriverNavigatorImpl(param) }

}